package com.darshan.personalitytest.question.viewmodel

import androidx.lifecycle.MutableLiveData
import com.darshan.personalitytest.question.domain.LoadQuestionUseCase
import com.darshan.personalitytest.question.model.Question
import javax.inject.Inject

class QuestionsViewModelImpl @Inject internal constructor(
    private val loadQuestionUseCase: LoadQuestionUseCase
) : QuestionsViewModel(), LoadQuestionUseCase.Callback {

    init {
        loadQuestionUseCase.setCallback(this)
    }

    override val state = MutableLiveData<State>()

    override fun getQuestions(category: String) {
        state.value = State.Loading
        loadQuestionUseCase.execute(category)
    }

    override fun onQuestionFetchSuccess(questions: List<Question>) {
        if (questions.isEmpty()) {
            state.value = State.Empty
        } else {
            state.value = State.Success(questions)
        }
    }

    override fun onQuestionFetchError(throwable: Throwable) {
        state.value = State.Error
    }

    public override fun onCleared() {
        super.onCleared()
        loadQuestionUseCase.cleanup()
    }

}