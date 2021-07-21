package com.darshan.personalitytest.question.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darshan.personalitytest.question.domain.LoadQuestionUseCase
import javax.inject.Inject

class QuestionsViewModelFactory @Inject constructor(
    private val loadQuestionUseCase: LoadQuestionUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        QuestionsViewModelImpl(
            loadQuestionUseCase
        ) as T

}