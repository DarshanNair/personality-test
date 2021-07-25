package com.darshan.personalitytest.question.domain.submitcategory

import com.darshan.core.domain.BaseUseCase
import com.darshan.core.injection.qualifiers.ForIoThread
import com.darshan.core.injection.qualifiers.ForMainThread
import com.darshan.database.room.entity.QuestionEntity
import com.darshan.network.model.QuestionData
import com.darshan.network.model.QuestionType
import com.darshan.personalitytest.question.repository.LoadQuestionRepository
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

class SubmitUseCaseImpl @Inject constructor(
    @Named("SubmitUseCase") compositeDisposable: CompositeDisposable,
    private val loadQuestionRepository: LoadQuestionRepository,
    private val gson: Gson,
    @ForIoThread private val ioScheduler: Scheduler,
    @ForMainThread private val mainScheduler: Scheduler
) : BaseUseCase(compositeDisposable), SubmitUseCase {

    private var callback: SubmitUseCase.Callback? = null

    /**
     * This function calls PATCH on individual items in given category.
     * We could have a single API which takes ID of all changed items. That way only one network suffice.
     */
    override fun execute(category: String) {
        trackDisposable(
            loadQuestionRepository.getQuestionsByCategory(category)
                .toObservable()
                .flatMap {
                    val work = getNetworkFormatContent(it)
                    Observable.zip(work, {})
                }
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({ onSuccess() }, ::onError)
        )
    }

    override fun setCallback(callback: SubmitUseCase.Callback) {
        this.callback = callback
    }

    override fun cleanup() {
        callback = null
        super.cleanup()
    }

    private fun onSuccess() {
        callback?.onSubmitSuccess()
    }

    private fun onError(throwable: Throwable) {
        callback?.onSubmitError(throwable)
    }

    private fun getNetworkFormatContent(questionEntities: List<QuestionEntity>): List<Observable<QuestionData>> {
        val updateQuestionRequests = mutableListOf<Observable<QuestionData>>()
        questionEntities.forEach {
            val questionData = QuestionData(
                id = it.id,
                question = it.question,
                category = it.category,
                questionType = QuestionType(
                    type = it.question_type,
                    selectedOption = it.question_option_selected,
                    options = gson.fromJson(
                        it.question_options,
                        List::class.java
                    ) as? List<String> ?: emptyList()
                )
            )
            updateQuestionRequests.add(
                loadQuestionRepository.updateQuestion(
                    questionData.id, questionData
                )
            )
        }

        return updateQuestionRequests
    }

}
