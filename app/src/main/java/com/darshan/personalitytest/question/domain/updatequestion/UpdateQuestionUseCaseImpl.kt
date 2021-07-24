package com.darshan.personalitytest.question.domain.updatequestion

import com.darshan.core.domain.BaseUseCase
import com.darshan.core.injection.qualifiers.ForIoThread
import com.darshan.core.injection.qualifiers.ForMainThread
import com.darshan.personalitytest.question.model.Question
import com.darshan.personalitytest.question.repository.LoadQuestionRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class UpdateQuestionUseCaseImpl @Inject constructor(
    @Named("UpdateQuestionUseCase") compositeDisposable: CompositeDisposable,
    private val loadQuestionRepository: LoadQuestionRepository,
    @ForIoThread private val ioScheduler: Scheduler,
    @ForMainThread private val mainScheduler: Scheduler
) : BaseUseCase(compositeDisposable), UpdateQuestionUseCase {

    override fun execute(question: Question) {
        trackDisposable(
            loadQuestionRepository.getQuestion(question.question)
                .map { questionEntity ->
                    questionEntity.question_option_selected = question.selectedOption.orEmpty()
                    loadQuestionRepository.insertQuestions(questionEntity)
                }
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({}, {})
        )
    }

}
