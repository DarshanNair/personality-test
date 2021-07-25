package com.darshan.personalitytest

import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.darshan.coretesting.CustomMatchers.Companion.clickRadioButtonWithTitle
import com.darshan.coretesting.CustomMatchers.Companion.withItemCount
import com.darshan.coretesting.EspressoIdlingResource
import com.darshan.coretesting.MockServerDispatcher
import com.darshan.coretesting.RecyclerViewMatcher
import com.darshan.coretesting.ToastMatcher
import com.darshan.personalitytest.category.view.adapter.CategoryViewHolder
import com.darshan.personalitytest.main.MainActivity
import com.darshan.personalitytest.question.view.adapter.QuestionsViewHolder
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
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

    companion object {
        private const val RECYCLERVIEW_CATEGORY_POSITION = 0
        private const val RECYCLERVIEW_QUESTION_POSITION = 0
        private const val CHOOSEN_CATEGORY = "HARD_FACT"
    }

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
    fun runSanityOnApp() {
        launchAndCheckCategoryListFragment()

        launchAndCheckQuestionsFragment()

        clickRadioButtonInQuestionsFragment()

        goBackToCategoryListFragment()

        relaunchQuestionsFragmentAndCheckRadioState()

        relaunchQuestionsFragmentAndSubmitPersonalityTest()
    }

    private fun launchAndCheckCategoryListFragment() {
        // GIVEN
        mockWebServer.dispatcher =
            MockServerDispatcher.SUCCESS_REQUEST(this.javaClass, "response-categories-success.json")

        // WHEN
        activityRule.launchActivity(null)

        // THEN
        onView(withId(R.id.view_category_list_loaded)).check(matches(isDisplayed()))
        onView(withId(R.id.view_category_list_loaded)).check(matches(withItemCount(4)))
    }

    private fun launchAndCheckQuestionsFragment() {
        //GIVEN
        mockWebServer.dispatcher =
            MockServerDispatcher.SUCCESS_REQUEST(this.javaClass, "response-questions-success.json")

        //WHEN
        onView(withId(R.id.view_category_list_loaded))
            .perform(
                actionOnItemAtPosition<CategoryViewHolder>(
                    RECYCLERVIEW_CATEGORY_POSITION,
                    click()
                )
            )

        //THEN
        onView(withId(R.id.selected_category_title)).check(matches(withText("Please provide your inputs for : $CHOOSEN_CATEGORY")))
        onView(withId(R.id.questions_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.questions_recycler_view)).check(matches(withItemCount(5)))
    }

    private fun clickRadioButtonInQuestionsFragment() {
        onView(withId(R.id.questions_recycler_view)).perform(
            actionOnItemAtPosition<QuestionsViewHolder>(
                RECYCLERVIEW_QUESTION_POSITION,
                clickRadioButtonWithTitle(R.id.radio_content, "male")
            )
        )
    }

    private fun goBackToCategoryListFragment() {
        Espresso.pressBack()
    }

    private fun relaunchQuestionsFragmentAndCheckRadioState() {
        //WHEN
        onView(withId(R.id.view_category_list_loaded))
            .perform(
                actionOnItemAtPosition<CategoryViewHolder>(
                    RECYCLERVIEW_CATEGORY_POSITION,
                    click()
                )
            )

        //THEN
        onView(withId(R.id.questions_recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.questions_recycler_view)).check(matches(withItemCount(5)))

        onView(RecyclerViewMatcher(R.id.questions_recycler_view).atPosition(0)).check { view, _ ->
            val radioContentLayout = view.findViewById<FrameLayout>(R.id.radio_content)
            val radioGroup = radioContentLayout.getChildAt(0) as RadioGroup
            val selectedRadioButton = radioGroup.getChildAt(0) as RadioButton

            selectedRadioButton.isChecked shouldBeEqualTo true
        }
    }

    private fun relaunchQuestionsFragmentAndSubmitPersonalityTest() {
        //GIVEN
        onView(withId(R.id.view_category_list_loaded))
            .perform(
                actionOnItemAtPosition<CategoryViewHolder>(
                    RECYCLERVIEW_CATEGORY_POSITION,
                    click()
                )
            )
        mockWebServer.dispatcher =
            MockServerDispatcher.SUCCESS_REQUEST(
                this.javaClass,
                "response-questions-submit-success.json"
            )

        //WHEN
        onView(withId(R.id.submit_button)).perform(click())

        //THEN
        onView(withText("Submit Success")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

}