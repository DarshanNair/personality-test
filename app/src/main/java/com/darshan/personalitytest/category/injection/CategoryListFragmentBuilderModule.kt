package com.darshan.personalitytest.category.injection

import com.darshan.personalitytest.category.view.CategoryListFragment
import com.darshan.personalitytest.core.injection.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoryListFragmentBuilderModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [CategoryListFragmentModule::class])
    abstract fun bindCategoryListFragment(): CategoryListFragment

}