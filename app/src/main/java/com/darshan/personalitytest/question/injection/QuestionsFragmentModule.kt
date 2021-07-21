package com.darshan.personalitytest.question.injection

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.core.injection.qualifiers.ForFragment
import com.darshan.personalitytest.core.injection.scopes.PerFragment
import com.darshan.personalitytest.question.view.QuestionsFragment
import dagger.Module
import dagger.Provides

@Module
class QuestionsFragmentModule {

    @Provides
    @PerFragment
    @ForFragment
    internal fun provideContext(fragment: QuestionsFragment): Context = fragment.requireContext()

    @Provides
    @PerFragment
    internal fun provideLayoutManager(@ForFragment context: Context): RecyclerView.LayoutManager =
        LinearLayoutManager(context)

    @Provides
    @PerFragment
    internal fun provideLayoutInflator(@ForFragment context: Context) = LayoutInflater.from(context)

}