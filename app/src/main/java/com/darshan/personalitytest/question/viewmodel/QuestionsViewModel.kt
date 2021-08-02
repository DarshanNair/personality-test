package com.darshan.personalitytest.question.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.darshan.personalitytest.question.model.Question

abstract class QuestionsViewModel : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val questions: List<Question>) : State()
        object Error : State()
        data class SubmitButtonState(val isEnabled: Boolean) : State()
        object Submitting : State()
        object SubmitSuccess : State()
        object SubmitFailed : State()
    }

    abstract val state: LiveData<State>

    abstract fun getQuestions(category: String)

    abstract fun updateQuestion(question: Question)

    abstract fun submit(category: String)

}
