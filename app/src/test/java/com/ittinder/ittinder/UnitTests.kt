package com.avans.avd.thebookclub

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.avans.avd.thebookclub.apiServices.BookAPIService
import com.avans.avd.thebookclub.entity.Book
import com.avans.avd.thebookclub.viewModels.BookViewmodel
import org.junit.Before
import org.junit.Test
import com.avans.avd.thebookclub.repositories.BookRepository
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
class BookViewmodelTest {

    // System under test
    private lateinit var sut: BookViewmodel

    // Mocks
    private val repository = mockk<BookRepository>()
    private val api = mockk<BookAPIService>()
    private val context = mockk<Context>()
    private val book = mockk<Book>()

    // Instantly update LiveData
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        // Set up sut
        sut = BookViewmodel(
            bookRepository = repository,
            bookAPIService = api,
        )

        // Allow suspend functions on main thread
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @After
    fun after() {
        // Restore main thread
        Dispatchers.resetMain()
    }

    @Test
    fun addBook_ShouldAddABook() {
        coEvery { api.addBooks(any()) } returns book

        sut.addABook(
            isbn = 0L,
            title = "title",
            authorName = "authorName",
            summary = "summary",
            publishingYear = 2000
        )

        coVerify { api.addBooks(any()) }
    }

    @Test
    fun `getASingleBook for non existing ISBN, results in value null`() {
        sut.getASingleBook(context, 0L)

        assertNull(sut.singlebook.value)
    }

    @Test
    fun `getASingleBook for an exisitng ISBN, results in value changed`() {
        coEvery { repository.getSingleBook(any(), any()) } returns book

        sut.getASingleBook(context, 0L)

        assertEquals(book, sut.singlebook.value)
    }
}