package com.darshan.personalitytest.question.viewmodel

import androidx.lifecycle.MutableLiveData
import com.darshan.personalitytest.question.domain.loadquestion.LoadQuestionUseCase
import com.darshan.personalitytest.question.domain.submitcategory.SubmitUseCase
import com.darshan.personalitytest.question.domain.updatequestion.UpdateQuestionUseCase
import com.darshan.personalitytest.question.model.Question
import javax.inject.Inject

class QuestionsViewModelImpl @Inject internal constructor(
    private val loadQuestionUseCase: LoadQuestionUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase,
    private val submitUseCase: SubmitUseCase
) : QuestionsViewModel(), LoadQuestionUseCase.Callback, SubmitUseCase.Callback {

    init {
        loadQuestionUseCase.setCallback(this)
        submitUseCase.setCallback(this)
    }

    override val state = MutableLiveData<State>()

    override fun getQuestions(category: String) {
        state.value = State.Loading
        loadQuestionUseCase.execute(category)
    }

    override fun updateQuestion(question: Question) {
        updateQuestionUseCase.execute(question)
    }

    override fun submit(category: String) {
        state.value = State.Submitting
        submitUseCase.execute(category)
    }

    override fun onQuestionFetchSuccess(questions: List<Question>) {
        state.value = State.Success(questions)
    }

    override fun onQuestionFetchError(throwable: Throwable) {
        state.value = State.Error
    }

    override fun onSubmitSuccess() {
        state.value = State.SubmitSuccess
    }

    override fun onSubmitError(throwable: Throwable) {
        state.value = State.SubmitFailed
    }

    public override fun onCleared() {
        super.onCleared()
        loadQuestionUseCase.cleanup()
        updateQuestionUseCase.cleanup()
        submitUseCase.cleanup()
    }

}