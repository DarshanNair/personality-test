package com.darshan.personalitytest.question.domain.updatequestion

import com.darshan.personalitytest.core.database.room.entity.QuestionEntity
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
class UpdateQuestionUseCaseTest {

    private lateinit var subject: UpdateQuestionUseCase

    @Mock
    private lateinit var mockCompositeDisposable: CompositeDisposable

    @Mock
    private lateinit var mockLoadQuestionRepository: LoadQuestionRepository

    @Mock
    private lateinit var mockQuestion: Question

    @Before
    fun setUp() {
        subject = UpdateQuestionUseCaseImpl(
            mockCompositeDisposable,
            mockLoadQuestionRepository,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Test
    fun `Update Question`() {
        //GIVEN
        val questionEntity = QuestionEntity(
            "QUESTION",
            "CATEGORY",
            "TYPE",
            "OPTIONS"
        )
        given(mockQuestion.question).willReturn("QUESTION")
        given(mockQuestion.selectedOption).willReturn("SELECTED_OPTION")
        given(mockLoadQuestionRepository.getQuestion("QUESTION"))
            .willReturn(Single.just(questionEntity))

        // WHEN
        subject.execute(mockQuestion)

        // THEN
        then(mockLoadQuestionRepository).should().getQuestion("QUESTION")
        then(mockLoadQuestionRepository).should().insertQuestions(
            QuestionEntity(
                "QUESTION",
                "CATEGORY",
                "TYPE",
                "OPTIONS",
                "SELECTED_OPTION"
            )
        )
        then(mockLoadQuestionRepository).shouldHaveNoMoreInteractions()
    }

}