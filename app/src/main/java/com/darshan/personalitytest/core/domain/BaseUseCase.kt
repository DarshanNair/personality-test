package com.darshan.personalitytest.core.domain

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseUseCase constructor(
    private val compositeDisposable: CompositeDisposable
) : UseCase {

    override fun trackDisposable(disposable: Disposable): Disposable {
        compositeDisposable.add(disposable)
        return disposable
    }

    override fun cleanup() {
        compositeDisposable.clear()
    }

}