package com.darshan.personalitytest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.darshan.coretesting.MockServerDispatcher
import com.darshan.personalitytest.category.view.adapter.CategoryViewHolder
import com.darshan.personalitytest.core.testutil.EspressoIdlingResource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SanityTest {

    private val mockWebServer = MockWebServer()

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        mockWebServer.start(8080)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        mockWebServer.shutdown()
    }

    @Test
    fun isCategoryListVisible_onAppLaunch() {
        launchAndCheckCategoryListFragment()

        launchAndCheckQuestionsFragment()
    }

    private fun launchAndCheckCategoryListFragment() {
        // GIVEN
        MockServerDispatcher.SUCCESS_REQUEST(this.javaClass, "response-questions-success.json")

        // WHEN
        activityRule.launchActivity(null)

        // THEN
        onView(withId(R.id.view_category_list_loaded)).check(matches(isDisplayed()))
    }

    private fun launchAndCheckQuestionsFragment() {
        //GIVEN
        MockServerDispatcher.SUCCESS_REQUEST(this.javaClass, "response-questions-success.json")

        //WHEN
        onView(withId(R.id.view_category_list_loaded))
            .perform(actionOnItemAtPosition<CategoryViewHolder>(0, click()))

        //THEN
        onView(withId(R.id.view_questions_loaded)).check(matches(isDisplayed()))
    }

}