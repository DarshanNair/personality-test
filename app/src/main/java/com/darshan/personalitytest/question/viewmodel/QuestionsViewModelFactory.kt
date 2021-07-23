package com.darshan.personalitytest.question.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darshan.personalitytest.question.domain.loadquestion.LoadQuestionUseCase
import com.darshan.personalitytest.question.domain.updatequestion.UpdateQuestionUseCase
import javax.inject.Inject

class QuestionsViewModelFactory @Inject constructor(
    private val loadQuestionUseCase: LoadQuestionUseCase,
    private val updateQuestionUseCase: UpdateQuestionUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        QuestionsViewModelImpl(
            loadQuestionUseCase,
            updateQuestionUseCase
        ) as T

}