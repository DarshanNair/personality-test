package com.darshan.personalitytest.question.domain.loadquestion

import com.darshan.personalitytest.core.network.model.QuestionData
import com.darshan.personalitytest.core.network.model.QuestionType
import com.darshan.personalitytest.question.model.Question
import com.darshan.personalitytest.question.repository.LoadQuestionRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadQuestionUseCaseTest {

    private lateinit var subject: LoadQuestionUseCase

    @Mock
    private lateinit var mockCallback: LoadQuestionUseCase.Callback

    @Mock
    private lateinit var mockThrowable: Throwable

    @Mock
    private lateinit var mockCompositeDisposable: CompositeDisposable

    @Mock
    private lateinit var mockLoadQuestionRepository: LoadQuestionRepository

    @Before
    fun setUp() {
        subject = LoadQuestionUseCaseImpl(
            mockCompositeDisposable,
            mockLoadQuestionRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
        subject.setCallback(mockCallback)
    }

    @Test
    fun `Load Question - Success`() {
        //GIVEN
        val questionData = QuestionData(
            "QUESTION",
            "CATEGORY",
            QuestionType(
                "TYPE",
                "SELECTED_OPTION",
                listOf("OPTIONS")
            )
        )
        given(mockLoadQuestionRepository.getQuestions("CATEGORY")).willReturn(
            Single.just(
                listOf(
                    questionData
                )
            )
        )

        // WHEN
        subject.execute("CATEGORY")

        // THEN
        then(mockCallback).should().onQuestionFetchSuccess(
            listOf(
                Question(
                    "QUESTION",
                    "CATEGORY",
                    listOf("OPTIONS"),
                    "SELECTED_OPTION"
                )
            )
        )
        then(mockCallback).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `Load Question - Error`() {
        // GIVEN
        given(mockLoadQuestionRepository.getQuestions("CATEGORY")).willReturn(
            Single.error(
                mockThrowable
            )
        )

        // WHEN
        subject.execute("CATEGORY")

        // THEN
        then(mockCallback).should().onQuestionFetchError(mockThrowable)
        then(mockCallback).shouldHaveNoMoreInteractions()
    }

    @Test
    fun `cleanup()`() {
        // WHEN
        subject.cleanup()

        // THEN
        then(mockCompositeDisposable).should().clear()
    }

}