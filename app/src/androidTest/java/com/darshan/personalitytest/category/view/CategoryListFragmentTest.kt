package com.darshan.personalitytest.category.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.darshan.coretesting.CustomMatchers.Companion.withItemCount
import com.darshan.coretesting.EspressoIdlingResource
import com.darshan.coretesting.MockServerDispatcher
import com.darshan.coretesting.RecyclerViewMatcher
import com.darshan.personalitytest.R
import com.darshan.personalitytest.category.model.Category
import com.darshan.personalitytest.main.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.IsNot.not
import org.junit.*

class CategoryListFragmentTest {

    private val mockWebServer = MockWebServer()

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val displayedCategories = listOf(
        Category("hard_fact"),
        Category("lifestyle"),
        Category("introversion"),
        Category("passion")
    )

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        mockWebServer.shutdown()
    }

    @Test
    fun test_launchCategoryListFragment_checkRecyclerViewContent() {
        // GIVEN
        mockWebServer.dispatcher = MockServerDispatcher.SUCCESS_REQUEST(
            this.javaClass,
            "response-categories-success.json"
        )

        // WHEN
        launchCategoryListFragment()

        // THEN
        onView(withId(R.id.view_category_list_loaded)).check(matches(isDisplayed()))
        onView(withId(R.id.view_category_list_loaded))
            .check(matches(withItemCount(displayedCategories.size)))
        displayedCategories.forEachIndexed { index, category ->
            onView(RecyclerViewMatcher(R.id.view_category_list_loaded).atPosition(index))
                .check(matches(hasDescendant(withText(category.name.uppercase()))))
        }
    }

    @Test
    fun test_launchCategoryListFragment_checkErrorState() {
        // GIVEN
        mockWebServer.dispatcher = MockServerDispatcher.ERROR_REQUEST(
            this.javaClass,
            "response-categories-error.json"
        )

        // WHEN
        launchCategoryListFragment()

        // THEN
        onView(withId(R.id.view_category_list_loaded)).check(matches(not(isDisplayed())))
        onView(withId(R.id.error_title)).check(matches(isDisplayed()))
        onView(withId(R.id.try_again_button)).check(matches(isDisplayed()))
    }

    @Test
    fun test_clickTryAgainOnError_refreshRecyclerViewContent() {
        // GIVEN
        test_launchCategoryListFragment_checkErrorState()

        // WHEN
        mockWebServer.dispatcher = MockServerDispatcher.SUCCESS_REQUEST(
            this.javaClass,
            "response-categories-success.json"
        )
        onView(withId(R.id.try_again_button)).perform(ViewActions.click())

        // THEN
        onView(withId(R.id.view_category_list_loaded)).check(matches(isDisplayed()))
        onView(withId(R.id.view_category_list_loaded))
            .check(matches(withItemCount(displayedCategories.size)))
        displayedCategories.forEachIndexed { index, category ->
            onView(RecyclerViewMatcher(R.id.view_category_list_loaded).atPosition(index))
                .check(matches(hasDescendant(withText(category.name.uppercase()))))
        }
    }

    private fun launchCategoryListFragment() {
        activityRule.launchActivity(null)
    }

}