package com.darshan.personalitytest.question.domain.submitcategory

import com.darshan.core.domain.UseCase

interface SubmitUseCase : UseCase {

    fun execute(category: String)

    fun setCallback(callback: Callback)

    interface Callback {
        fun onSubmitSuccess()
        fun onSubmitError(throwable: Throwable)
    }

}