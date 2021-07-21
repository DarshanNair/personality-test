package com.darshan.personalitytest.core.domain

import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

interface UseCase {

    fun trackDisposable(@NonNull disposable: Disposable): Disposable

    fun cleanup()

}