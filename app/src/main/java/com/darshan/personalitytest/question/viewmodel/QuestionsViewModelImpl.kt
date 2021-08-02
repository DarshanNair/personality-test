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

    private var questions = emptyList<Question>()

    override fun getQuestions(category: String) {
        state.value = State.Loading
        loadQuestionUseCase.execute(category)
    }

    override fun updateQuestion(question: Question) {
        updateQuestionUseCase.execute(question)
        state.value = State.SubmitButtonState(isAllRequiredFieldSelected)
    }

    override fun submit(category: String) {
        state.value = State.Submitting
        submitUseCase.execute(category)
    }

    override fun onQuestionFetchSuccess(questions: List<Question>) {
        this.questions = questions
        //Hack: Back -> Usecases
        questions[0].requiredField = true
        state.value = State.Success(questions)
        state.value = State.SubmitButtonState(isAllRequiredFieldSelected)
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

    private val isAllRequiredFieldSelected: Boolean
        get() = questions.filter { it.requiredField }.all { it.selectedOption != "" }

}