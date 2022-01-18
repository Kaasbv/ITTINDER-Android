package com.ittinder.ittinder

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiTests {
    val message = "Hoi"
    val firstname = "Cynthia"

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_NETWORK_STATE
    )

    @Before
    fun setup() {
        // Arrange
        launchActivity<MainActivity>()
        //Login
        onView(withId(R.id.emailAddressEditText)).perform(replaceText("test2@gmail.com"))
        onView(withId(R.id.passwordEditText)).perform(replaceText("Start1234%"))
        onView(withId(R.id.login)).perform(click())
        Thread.sleep(1000)
    }

    @Test
    fun chat_send_message(){
        //Act
        onView(withId(R.id.chatList)).perform(click())
        onView(withId(R.id.chat_recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0, click()) )
        onView(withId(R.id.chat_edittext)).perform(replaceText(message))
        onView(withId(R.id.chat_submit_button)).perform(click())
        //Assert
        Thread.sleep(2000)
        onView(withText(message)).check(matches(isDisplayed()))
    }

    @Test
    fun swipe_right(){
        //Act
        onView(withId(R.id.like)).perform(click())
        //Assert
        onView(withId(R.id.Name)).check(matches(withText("No available users to swipe")))
    }

    @Test
    fun profile_edit(){
        //Act
        onView(withId(R.id.profileFragment)).perform(click())
        onView(withId(R.id.EditTextFirstName)).perform(replaceText(firstname))
        onView(withId(R.id.update_profile_button)).perform(scrollTo(), click())
        Thread.sleep(2000)
        //Assert
        onView(withId(R.id.profileFragment)).perform(click())
        onView(withId(R.id.EditTextFirstName)).check(matches(withText(firstname)))
    }
}
