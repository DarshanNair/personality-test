package com.darshan.personalitytest.category.injection

import com.darshan.core.injection.scopes.PerFragment
import com.darshan.personalitytest.category.view.CategoryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoryListFragmentBuilderModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [CategoryListFragmentModule::class])
    abstract fun bindCategoryListFragment(): CategoryListFragment

}