package com.ittinder.ittinder

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ittinder.ittinder.data.LoginResponse
import com.ittinder.ittinder.data.RegisterObject
import org.junit.Before
import org.junit.Test
import com.ittinder.ittinder.data.User
import com.ittinder.ittinder.service.UserApiService
import com.ittinder.ittinder.viewmodel.UserViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Rule
import org.junit.rules.TestRule


@ExperimentalCoroutinesApi
class UserViewModelTest {

    // System under test
    private lateinit var sut: UserViewModel

    // Mocks
    private val api = mockk<UserApiService>()
    private val context = mockk<Context>()
    private val activity = mockk<Activity>()
    private val user = mockk<User>()
    private val registerObject = mockk<RegisterObject>()
    private val preferences = mockk<SharedPreferences>()
    private val preferencesEdit = mockk<SharedPreferences.Editor>()
    private val loginResponse = mockk<LoginResponse>()

    // Instantly update LiveData
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        // Set up sut
        sut = UserViewModel(api)

        // Allow suspend functions on main thread
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun after() {
        // Restore main thread
        Dispatchers.resetMain()
    }

    @Test
    fun registerUserTest() {
        coEvery { api.register(any()) } returns "SUCCESS"
        sut.register(registerObject)
        coVerify { api.register(any()) }
    }

    @Test
    fun getUserTest() {
        coEvery { api.getUser(any()) } returns user
        sut.getUser(activity)

        assertNull(sut.user.value)
    }

    @Test
    fun loginUsertest() {
        coEvery { api.loginUser(any()) } returns loginResponse
        coEvery { activity.getPreferences(any()) } returns preferences
        coEvery { preferences.edit() } returns preferencesEdit
        coEvery { loginResponse.sessionId } returns ""
        coEvery { loginResponse.user.id } returns 1
        coEvery { preferencesEdit.putString(any(), any()) } returns preferencesEdit
        coEvery { preferencesEdit.putLong(any(), any()) } returns preferencesEdit
        coEvery { preferencesEdit.apply() } returns Unit

        val response = sut.login("paul@gmail.com", "Start1234%", activity)

        coVerify { preferencesEdit.putString(any(), any()) }
        coVerify { preferencesEdit.putLong(any(), any()) }
        assertEquals(response.value, true)
    }
}