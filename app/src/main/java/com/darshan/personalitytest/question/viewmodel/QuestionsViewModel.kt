package com.darshan.personalitytest.question.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.darshan.personalitytest.question.model.Question

abstract class QuestionsViewModel : ViewModel() {

    sealed class State {
        object Loading : State()
        data class Success(val questions: List<Question>) : State()
        object Empty : State()
        object Error : State()
    }

    abstract val state: LiveData<State>

    abstract fun getQuestions(category: String)

}
