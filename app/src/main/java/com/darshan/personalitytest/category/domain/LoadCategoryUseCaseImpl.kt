package com.darshan.personalitytest.category.domain

import com.darshan.personalitytest.category.model.Category
import com.darshan.personalitytest.category.repository.LoadCategoryRepository
import com.darshan.personalitytest.core.domain.BaseUseCase
import com.darshan.personalitytest.core.injection.qualifiers.ForIoThread
import com.darshan.personalitytest.core.injection.qualifiers.ForMainThread
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoadCategoryUseCaseImpl @Inject constructor(
    compositeDisposable: CompositeDisposable,
    private val loadCategoryRepository: LoadCategoryRepository,
    @ForIoThread private val ioScheduler: Scheduler,
    @ForMainThread private val mainScheduler: Scheduler
) : BaseUseCase(compositeDisposable), LoadCategoryUseCase {

    private var callback: LoadCategoryUseCase.Callback? = null

    override fun execute() {
        trackDisposable(
            loadCategoryRepository.getCategories()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(::onSuccess, ::onError)
        )
    }

    override fun setCallback(callback: LoadCategoryUseCase.Callback) {
        this.callback = callback
    }

    override fun cleanup() {
        callback = null
        super.cleanup()
    }

    private fun onSuccess(deals: List<Category>) {
        callback?.onCategoryFetchSuccess(deals)
    }

    private fun onError(throwable: Throwable) {
        callback?.onCategoryFetchError(throwable)
    }

}
