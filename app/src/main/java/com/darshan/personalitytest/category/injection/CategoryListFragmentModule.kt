package com.darshan.personalitytest.category.injection

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darshan.core.injection.qualifiers.ForFragment
import com.darshan.core.injection.scopes.PerFragment
import com.darshan.personalitytest.category.domain.injection.LoadCategoryUseCaseModule
import com.darshan.personalitytest.category.view.CategoryListFragment
import com.darshan.personalitytest.category.viewmodel.CategoryListViewModel
import com.darshan.personalitytest.category.viewmodel.CategoryListViewModelFactory
import dagger.Module
import dagger.Provides

@Module(includes = [LoadCategoryUseCaseModule::class])
class CategoryListFragmentModule {

    @Provides
    @PerFragment
    @ForFragment
    internal fun provideContext(fragment: CategoryListFragment): Context = fragment.requireContext()

    @Provides
    @PerFragment
    internal fun provideLayoutManager(@ForFragment context: Context): RecyclerView.LayoutManager =
        LinearLayoutManager(context)

    @Provides
    @PerFragment
    internal fun provideLayoutInflator(@ForFragment context: Context) = LayoutInflater.from(context)

    @Provides
    @PerFragment
    fun provideCategoryListViewModel(
        fragment: CategoryListFragment,
        factory: CategoryListViewModelFactory
    ): CategoryListViewModel =
        ViewModelProvider(fragment, factory).get(CategoryListViewModel::class.java)

}