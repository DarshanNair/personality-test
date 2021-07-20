package com.darshan.personalitytest.category.injection

import android.content.Context
import com.darshan.personalitytest.category.view.CategoryListFragment
import com.darshan.personalitytest.core.injection.qualifiers.ForFragment
import com.darshan.personalitytest.core.injection.scopes.PerFragment
import dagger.Module
import dagger.Provides

@Module
class CategoryListFragmentModule {

    @Provides
    @PerFragment
    @ForFragment
    internal fun provideContext(fragment: CategoryListFragment): Context = fragment.requireContext()

}