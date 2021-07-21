package com.darshan.personalitytest.question.domain

import com.darshan.personalitytest.core.domain.BaseUseCase
import com.darshan.personalitytest.core.injection.qualifiers.ForIoThread
import com.darshan.personalitytest.core.injection.qualifiers.ForMainThread
import com.darshan.personalitytest.question.model.Question
import com.darshan.personalitytest.question.repository.LoadQuestionRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoadQuestionUseCaseImpl @Inject constructor(
    compositeDisposable: CompositeDisposable,
    private val loadQuestionRepository: LoadQuestionRepository,
    @ForIoThread private val ioScheduler: Scheduler,
    @ForMainThread private val mainScheduler: Scheduler
) : BaseUseCase(compositeDisposable), LoadQuestionUseCase {

    private var callback: LoadQuestionUseCase.Callback? = null

    override fun execute(category: String) {
        trackDisposable(
            loadQuestionRepository.getQuestions(category)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(::onSuccess, ::onError)
        )
    }

    override fun setCallback(callback: LoadQuestionUseCase.Callback) {
        this.callback = callback
    }

    override fun cleanup() {
        callback = null
        super.cleanup()
    }

    private fun onSuccess(deals: List<Question>) {
        callback?.onQuestionFetchSuccess(deals)
    }

    private fun onError(throwable: Throwable) {
        callback?.onQuestionFetchError(throwable)
    }

}
