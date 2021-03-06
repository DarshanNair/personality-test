package com.darshan.personalitytest.category.domain

import com.darshan.core.domain.BaseUseCase
import com.darshan.core.injection.qualifiers.ForIoThread
import com.darshan.core.injection.qualifiers.ForMainThread
import com.darshan.personalitytest.category.model.Category
import com.darshan.personalitytest.category.repository.LoadCategoryRepository
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

    private fun onSuccess(categoryData: List<String>) {
        val categories = mutableListOf<Category>()
        categoryData.forEach {
            categories.add(Category(it))
        }
        callback?.onCategoryFetchSuccess(categories)
    }

    private fun onError(throwable: Throwable) {
        callback?.onCategoryFetchError(throwable)
    }

}
