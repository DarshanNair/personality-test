package com.darshan.personalitytest.question.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.databinding.FragmentQuestionsBinding
import com.darshan.personalitytest.question.view.adapter.QuestionsAdapter
import com.darshan.personalitytest.question.viewmodel.QuestionsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class QuestionsFragment : Fragment() {

    private var _binding: FragmentQuestionsBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var questionsAdapter: QuestionsAdapter

    @Inject
    lateinit var questionsLayoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var questionsViewModel: QuestionsViewModel

    companion object {
        private const val KEY_CATEGORY_ID = "KEY_CATEGORY_ID"
        fun newInstance(categoryId: String) = QuestionsFragment().apply {
            arguments = Bundle().apply { putString(KEY_CATEGORY_ID, categoryId) }
        }
    }

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
        setupRecyclerView()

        questionsViewModel.apply {
            state.observe(viewLifecycleOwner, { it?.let { onCategoryLoaded(it) } })
            arguments?.getString(KEY_CATEGORY_ID)?.let {
                getQuestions(it)
            }

        }
    }

    private fun setupRecyclerView() {
        binding.questionsRecyclerView.let {
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

            }
            is QuestionsViewModel.State.Success -> {
                questionsAdapter.setQuestion(state.questions)
            }
            QuestionsViewModel.State.Empty -> {
                //TODO
            }
            QuestionsViewModel.State.Error -> {
                //TODO
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
