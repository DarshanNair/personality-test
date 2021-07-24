package com.darshan.personalitytest.question.domain.submitcategory

import com.darshan.core.domain.BaseUseCase
import com.darshan.core.injection.qualifiers.ForIoThread
import com.darshan.core.injection.qualifiers.ForMainThread
import com.darshan.personalitytest.question.model.Question
import com.darshan.personalitytest.question.repository.LoadQuestionRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class SubmitUseCaseImpl @Inject constructor(
    @Named("SubmitUseCase") compositeDisposable: CompositeDisposable,
    private val loadQuestionRepository: LoadQuestionRepository,
    @ForIoThread private val ioScheduler: Scheduler,
    @ForMainThread private val mainScheduler: Scheduler
) : BaseUseCase(compositeDisposable), SubmitUseCase {

    override fun execute(question: Question) {
        trackDisposable(
            loadQuestionRepository.getQuestionsByCategory(question.category)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({}, {})
        )
    }

}
