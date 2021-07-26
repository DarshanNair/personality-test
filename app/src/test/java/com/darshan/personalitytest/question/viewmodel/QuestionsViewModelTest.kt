package com.darshan.personalitytest.question.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.darshan.personalitytest.question.domain.loadquestion.LoadQuestionUseCase
import com.darshan.personalitytest.question.domain.submitcategory.SubmitUseCase
import com.darshan.personalitytest.question.domain.updatequestion.UpdateQuestionUseCase
import com.darshan.personalitytest.question.model.Question
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class QuestionsViewModelTest {

    private lateinit var subject: QuestionsViewModelImpl

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockLoadQuestionUseCase: LoadQuestionUseCase

    @Mock
    private lateinit var mockUpdateQuestionUseCase: UpdateQuestionUseCase

    @Mock
    private lateinit var mockSubmitUseCase: SubmitUseCase

    @Mock
    private lateinit var mockObserver: Observer<QuestionsViewModel.State>

    @Mock
    private lateinit var mockThrowable: Throwable

    @Mock
    private lateinit var mockQuestion: Question

    private lateinit var stateLiveData: MutableLiveData<QuestionsViewModel.State>

    companion object {
        private const val CATEGORY = "CATEGORY"
    }

    @Before
    fun setUp() {
        stateLiveData = MutableLiveData()
        subject = QuestionsViewModelImpl(
            mockLoadQuestionUseCase,
            mockUpdateQuestionUseCase,
            mockSubmitUseCase
        )
        subject.state.observeForever(mockObserver)
    }

    @Test
    fun `Load questions`() {
        // GIVEN
        Mockito.reset(mockLoadQuestionUseCase, mockSubmitUseCase)

        //WHEN
        subject.getQuestions(CATEGORY)

        //THEN
        thenObserverShouldReceiveCorrectStates(QuestionsViewModel.State.Loading)
        then(mockLoadQuestionUseCase).should().execute(CATEGORY)
        then(mockLoadQuestionUseCase).shouldHaveNoMoreInteractions()
        then(mockUpdateQuestionUseCase).shouldHaveNoInteractions()
        then(mockSubmitUseCase).shouldHaveNoInteractions()
    }

    @Test
    fun `Question Loaded - Success`() {
        // GIVEN
        val categories = listOf(mockQuestion)

        // WHEN
        subject.onQuestionFetchSuccess(categories)

        // THEN
        thenObserverShouldReceiveCorrectStates(QuestionsViewModel.State.Success(categories))
    }

    @Test
    fun `Question Update`() {
        // GIVEN
        Mockito.reset(mockSubmitUseCase)

        // WHEN
        subject.updateQuestion(mockQuestion)

        // THEN
        then(mockUpdateQuestionUseCase).should().execute(mockQuestion)
        then(mockUpdateQuestionUseCase).shouldHaveNoMoreInteractions()
        then(mockSubmitUseCase).shouldHaveNoInteractions()
    }

    @Test
    fun `Question Loaded - Error`() {
        // WHEN
        subject.onQuestionFetchError(mockThrowable)

        // THEN
        thenObserverShouldReceiveCorrectStates(QuestionsViewModel.State.Error)
    }

    @Test
    fun `On Cleared`() {
        // GIVEN
        Mockito.reset(mockLoadQuestionUseCase, mockUpdateQuestionUseCase, mockSubmitUseCase)

        // WHEN
        subject.onCleared()

        // THEN
        then(mockLoadQuestionUseCase).should().cleanup()
        then(mockUpdateQuestionUseCase).should().cleanup()
        then(mockSubmitUseCase).should().cleanup()
    }

    @Test
    fun `Submit questions for a category`() {
        // GIVEN
        Mockito.reset(mockLoadQuestionUseCase, mockSubmitUseCase)

        //WHEN
        subject.submit(CATEGORY)

        //THEN
        thenObserverShouldReceiveCorrectStates(QuestionsViewModel.State.Submitting)
        then(mockSubmitUseCase).should().execute(CATEGORY)
        then(mockSubmitUseCase).shouldHaveNoMoreInteractions()
        then(mockLoadQuestionUseCase).shouldHaveNoInteractions()
        then(mockUpdateQuestionUseCase).shouldHaveNoInteractions()
    }

    @Test
    fun `Submit questions - Success`() {
        // GIVEN
        val categories = listOf(mockQuestion)

        // WHEN
        subject.onSubmitSuccess()

        // THEN
        thenObserverShouldReceiveCorrectStates(QuestionsViewModel.State.SubmitSuccess)
    }

    @Test
    fun `Submit questions - Error`() {
        // WHEN
        subject.onSubmitError(mockThrowable)

        // THEN
        thenObserverShouldReceiveCorrectStates(QuestionsViewModel.State.SubmitFailed)
    }

    private fun thenObserverShouldReceiveCorrectStates(vararg expected: QuestionsViewModel.State) {
        expected.forEach { then(mockObserver).should().onChanged(it) }
        then(mockObserver).shouldHaveNoMoreInteractions()
    }

}