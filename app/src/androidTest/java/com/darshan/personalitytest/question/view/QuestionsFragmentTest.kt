package com.darshan.personalitytest.question.view

import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.darshan.coretesting.*
import com.darshan.personalitytest.R
import com.darshan.personalitytest.category.view.adapter.CategoryViewHolder
import com.darshan.personalitytest.main.MainActivity
import com.darshan.personalitytest.question.model.Question
import com.darshan.personalitytest.question.view.adapter.QuestionsViewHolder
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.shouldBeEqualTo
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuestionsFragmentTest {

    private val mockWebServer = MockWebServer()

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    private val selectedCategoryPosition = 0
    private val selectedQuestionPosition = 0
    private val selectedCategoryType = "hard_fact"
    private val displayedQuestions = listOf(
        Question(
            "What is your gender?",
            "hard_fact",
            listOf("male", "female", "other")
        ),
        Question(
            "How important is the gender of your partner?",
            "hard_fact",
            listOf("not important", "important", "very important")
        ),
        Question(
            "How important is the age of your partner to you?",
            "hard_fact",
            listOf("not important", "important", "very important")
        ),
        Question(
            "Do any children under the age of 18 live with you?",
            "hard_fact",
            listOf("yes", "sometimes", "no")
        ),
        Question(
            "What is your marital status?",
            "hard_fact",
            listOf("never married", "separated", "divorced", "widowed")
        )
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
    fun test_launchQuestionsFragment_checkRecyclerViewContent() {
        //WHEN
        launchActivityWithQuestionsFragment()

        //THEN
        onView(withId(R.id.selected_category_title))
            .check(matches(withText("Please provide your inputs for : ${selectedCategoryType.uppercase()}")))
        onView(withId(R.id.questions_recycler_view))
            .check(matches(isDisplayed()))
        onView(withId(R.id.questions_recycler_view))
            .check(matches(CustomMatchers.withItemCount(displayedQuestions.size)))
        checkRadioStateAfterSelection()
    }

    @Test
    fun test_clickRadioButton_checkState() {
        //GIVEN
        launchActivityWithQuestionsFragment()

        //WHEN
        onView(withId(R.id.questions_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<QuestionsViewHolder>(
                selectedQuestionPosition,
                CustomMatchers.clickRadioButtonWithTitle(R.id.radio_content, "male")
            )
        )

        //THEN
        displayedQuestions[0].selectedOption = "male"
        checkRadioStateAfterSelection()
    }

    @Test
    fun test_relaunchQuestionsFragment_stateShouldPersist() {
        //GIVEN
        test_clickRadioButton_checkState()
        Espresso.pressBack()

        //When
        launchQuestionsFragment()

        //THEN
        displayedQuestions[0].selectedOption = "male"
        checkRadioStateAfterSelection()
    }

    @Test
    fun test_launchQuestionsFragment_checkErrorState() {
        // GIVEN
        launchActivityWithCategoryFragment()

        // WHEN
        mockWebServer.dispatcher = MockServerDispatcher.ERROR_REQUEST(
            this.javaClass,
            "response-questions-error.json"
        )
        onView(withId(R.id.view_category_list_loaded))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CategoryViewHolder>(
                    selectedCategoryPosition,
                    ViewActions.click()
                )
            )

        // THEN
        onView(withId(R.id.questions_recycler_view)).check(matches(IsNot.not(isDisplayed())))
        onView(allOf(withId(R.id.error_title), withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.try_again_button), withEffectiveVisibility(Visibility.VISIBLE)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_clickTryAgainOnError_refreshRecyclerViewContent() {
        //Given
        test_launchQuestionsFragment_checkErrorState()

        // WHEN
        mockWebServer.dispatcher = MockServerDispatcher.SUCCESS_REQUEST(
            this.javaClass,
            "response-questions-success.json"
        )
        onView(allOf(withId(R.id.try_again_button), withEffectiveVisibility(Visibility.VISIBLE)))
            .perform(ViewActions.click())

        //THEN
        checkRadioStateAfterSelection()
    }

    @Test
    fun test_submitPersonalityQuestions() {
        //GIVEN
        launchActivityWithQuestionsFragment()
        mockWebServer.dispatcher =
            MockServerDispatcher.SUCCESS_REQUEST(
                this.javaClass,
                "response-questions-submit-success.json"
            )

        //WHEN
        onView(withId(R.id.submit_button)).perform(ViewActions.click())

        //THEN
        onView(withText("Submit Success")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    private fun launchActivityWithQuestionsFragment() {
        launchActivityWithCategoryFragment()
        launchQuestionsFragment()
    }

    private fun launchActivityWithCategoryFragment() {
        mockWebServer.dispatcher = MockServerDispatcher.SUCCESS_REQUEST(
            this.javaClass,
            "response-categories-success.json"
        )

        activityRule.launchActivity(null)
    }

    private fun launchQuestionsFragment() {
        mockWebServer.dispatcher = MockServerDispatcher.SUCCESS_REQUEST(
            this.javaClass,
            "response-questions-success.json"
        )

        onView(withId(R.id.view_category_list_loaded))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<CategoryViewHolder>(
                    selectedCategoryPosition,
                    ViewActions.click()
                )
            )
    }

    private fun checkRadioStateAfterSelection() {
        displayedQuestions.forEachIndexed { index, question ->
            onView(withId(R.id.questions_recycler_view)).perform(
                scrollToPosition<RecyclerView.ViewHolder>(
                    index
                )
            );
            onView(RecyclerViewMatcher(R.id.questions_recycler_view).atPosition(index)).check { view, _ ->
                //check question
                val textView = view.findViewById<TextView>(R.id.question)
                textView.text shouldBeEqualTo question.question

                //check radio state
                val radioContentLayout = view.findViewById<FrameLayout>(R.id.radio_content)
                val radioGroup = radioContentLayout.getChildAt(0) as RadioGroup
                question.options.forEachIndexed { index, option ->
                    val selectedRadioButton = radioGroup.getChildAt(index) as RadioButton
                    selectedRadioButton.text shouldBeEqualTo option
                    val isChecked = selectedRadioButton.text == question.selectedOption
                    selectedRadioButton.isChecked shouldBeEqualTo isChecked
                }
            }
        }
    }

}