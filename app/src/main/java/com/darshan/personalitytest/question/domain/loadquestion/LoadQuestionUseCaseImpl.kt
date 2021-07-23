package com.darshan.personalitytest.question.domain.loadquestion

import com.darshan.personalitytest.core.domain.BaseUseCase
import com.darshan.personalitytest.core.injection.qualifiers.ForIoThread
import com.darshan.personalitytest.core.injection.qualifiers.ForMainThread
import com.darshan.personalitytest.core.network.model.QuestionData
import com.darshan.personalitytest.question.model.Question
import com.darshan.personalitytest.question.repository.LoadQuestionRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class LoadQuestionUseCaseImpl @Inject constructor(
    @Named("LoadQuestionUseCase") compositeDisposable: CompositeDisposable,
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

    private fun onSuccess(questionEntities: List<QuestionData>) {
        val questions = mutableListOf<Question>()
        questionEntities.forEach {
            questions.add(
                Question(
                    it.question,
                    it.category,
                    it.questionType.options,
                    it.questionType.selectedOption
                )
            )
        }
        callback?.onQuestionFetchSuccess(questions)
    }

    private fun onError(throwable: Throwable) {
        callback?.onQuestionFetchError(throwable)
    }

}
