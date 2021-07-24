package com.darshan.personalitytest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.darshan.personalitytest.category.view.FileReader
import com.darshan.personalitytest.core.testutil.EspressoIdlingResource
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
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
        val json =
            FileReader.readStringFromFile(this@SanityTest, "response-success.json")
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(json)
            }
        }

        activityRule.launchActivity(null)

        onView(withId(R.id.view_category_list_loaded))
            .check(matches(isDisplayed()))

        Thread.sleep(5000)
    }

    /*@Test
    fun selectCategoryItem_isQuestionsListVisible() {
        onView(withId(R.id.view_category_list_loaded))
            .perform(actionOnItemAtPosition<CategoryViewHolder>(0, click()))
        onView(withId(R.id.view_questions_loaded))
            .check(matches(isDisplayed()))
    }*/

    fun mockResponse(response: String) {

    }

}