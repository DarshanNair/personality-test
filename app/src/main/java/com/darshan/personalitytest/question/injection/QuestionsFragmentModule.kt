package com.darshan.personalitytest.question.injection

import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darshan.core.injection.qualifiers.ForFragment
import com.darshan.core.injection.scopes.PerFragment
import com.darshan.personalitytest.question.domain.loadquestion.injection.LoadQuestionUseCaseModule
import com.darshan.personalitytest.question.domain.submitcategory.injection.SubmitUseCaseModule
import com.darshan.personalitytest.question.domain.updatequestion.injection.UpdateQuestionUseCaseModule
import com.darshan.personalitytest.question.view.QuestionsFragment
import com.darshan.personalitytest.question.viewmodel.QuestionsViewModel
import com.darshan.personalitytest.question.viewmodel.QuestionsViewModelFactory
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        LoadQuestionUseCaseModule::class,
        UpdateQuestionUseCaseModule::class,
        SubmitUseCaseModule::class
    ]
)
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

    @Provides
    @PerFragment
    fun provideQuestionsViewModel(
        fragment: QuestionsFragment,
        factory: QuestionsViewModelFactory
    ): QuestionsViewModel =
        ViewModelProvider(fragment, factory).get(QuestionsViewModel::class.java)

}