package com.darshan.personalitytest.question.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.darshan.core.DeviceManager
import com.darshan.coretesting.EspressoIdlingResource
import com.darshan.personalitytest.R
import com.darshan.personalitytest.category.view.CategoryListFragment
import com.darshan.personalitytest.databinding.FragmentQuestionsBinding
import com.darshan.personalitytest.main.SubmitProgress
import com.darshan.personalitytest.main.viewmodel.SharedViewModel
import com.darshan.personalitytest.question.view.adapter.QuestionsAdapter
import com.darshan.personalitytest.question.viewmodel.QuestionsViewModel
import dagger.Lazy
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class QuestionsFragment : Fragment() {

    enum class UIState {
        LOADING,
        LOADED,
        ERROR,
        TABLET_EMPTY_VIEW
    }

    private var _binding: FragmentQuestionsBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var questionsAdapter: QuestionsAdapter

    @Inject
    lateinit var questionsLayoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var questionsViewModel: QuestionsViewModel

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var deviceManager: DeviceManager

    @Inject
    lateinit var submitProgress: Lazy<SubmitProgress>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
        setupRecyclerView()
        setupViewModel(savedInstanceState)
    }

    private fun setupViewModel(savedInstanceState: Bundle?) {
        questionsViewModel.apply {
            state.observe(viewLifecycleOwner, { it?.let { onCategoryLoaded(it) } })
        }

        sharedViewModel.state.observe(viewLifecycleOwner, { categoryId ->
            if (categoryId == null) {
                binding.viewFlipperQuestions.displayedChild = UIState.TABLET_EMPTY_VIEW.ordinal
            } else {
                EspressoIdlingResource.increment()
                questionsViewModel.getQuestions(categoryId)
            }
        })
    }

    private fun setupClickListener() {
        binding.viewGeneralError.tryAgainButton.setOnClickListener {
            sharedViewModel.state.value?.let {
                EspressoIdlingResource.increment()
                questionsViewModel.getQuestions(it)
            }
        }
        binding.viewQuestionsLoaded.submitButton.setOnClickListener {
            sharedViewModel.state.value?.let {
                EspressoIdlingResource.increment()
                questionsViewModel.submit(it)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.viewQuestionsLoaded.questionsRecyclerView.let {
            it.layoutManager = questionsLayoutManager
            it.adapter = questionsAdapter
        }
        questionsAdapter.setOnClickListener {
            questionsViewModel.updateQuestion(it)
        }
    }

    private fun onCategoryLoaded(state: QuestionsViewModel.State) {
        when (state) {
            QuestionsViewModel.State.Loading -> {
                binding.viewFlipperQuestions.displayedChild = UIState.LOADING.ordinal
            }
            is QuestionsViewModel.State.Success -> {
                binding.viewFlipperQuestions.displayedChild =
                    CategoryListFragment.UIState.LOADED.ordinal
                binding.viewQuestionsLoaded.selectedCategoryTitle.text =
                    getString(
                        R.string.category_title_selected,
                        state.questions[0].category.uppercase()
                    )
                questionsAdapter.setQuestion(state.questions)
                EspressoIdlingResource.decrement()
            }
            is QuestionsViewModel.State.SubmitButtonState -> {
                binding.viewQuestionsLoaded.submitButton.isEnabled = state.isEnabled
            }
            QuestionsViewModel.State.Error -> {
                binding.viewFlipperQuestions.displayedChild = UIState.ERROR.ordinal
                EspressoIdlingResource.decrement()
            }
            QuestionsViewModel.State.Submitting -> {
                submitProgress.get().show()
            }
            QuestionsViewModel.State.SubmitSuccess -> {
                submitProgress.get().hide()
                Toast.makeText(requireContext(), R.string.submitting_success, Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager.popBackStack()
                EspressoIdlingResource.decrement()
            }
            QuestionsViewModel.State.SubmitFailed -> {
                submitProgress.get().hide()
                AlertDialog.Builder(requireContext())
                    .setTitle(R.string.submitting_failed_title)
                    .setMessage(R.string.submitting_failed_description)
                    .setPositiveButton(R.string.submit_ok_button) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setCancelable(false)
                    .show()
                EspressoIdlingResource.decrement()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
