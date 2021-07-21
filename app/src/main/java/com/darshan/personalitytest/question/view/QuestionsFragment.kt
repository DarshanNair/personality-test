package com.darshan.personalitytest.question.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.databinding.FragmentQuestionsBinding
import com.darshan.personalitytest.question.model.Question
import com.darshan.personalitytest.question.view.adapter.QuestionsAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class QuestionsFragment : Fragment() {

    private var _binding: FragmentQuestionsBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var questionsAdapter: QuestionsAdapter

    @Inject
    lateinit var questionsLayoutManager: RecyclerView.LayoutManager

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
    }

    private fun setupRecyclerView() {
        binding.questionsRecyclerView.let {
            it.layoutManager = questionsLayoutManager
            it.adapter = questionsAdapter
            questionsAdapter.setOnClickListener {

            }
        }

        //TODO
        questionsAdapter.setQuestion(
            listOf(
                Question("What is your gender?", listOf("male", "female", "other")),
                Question(
                    "How important is the gender of your partner?",
                    listOf("not important", "important", "very important")
                ),
                Question(
                    "Do any children under the age of 18 live with you?",
                    listOf("yes", "sometimes", "no")
                ),
                Question("What is your gender?", listOf("male", "female", "other")),
                Question(
                    "How important is the gender of your partner?",
                    listOf("not important", "important", "very important")
                ),
                Question(
                    "Do any children under the age of 18 live with you?",
                    listOf("yes", "sometimes", "no")
                ),
                Question("What is your gender?", listOf("male", "female", "other")),
                Question(
                    "How important is the gender of your partner?",
                    listOf("not important", "important", "very important")
                ),
                Question(
                    "Do any children under the age of 18 live with you?",
                    listOf("yes", "sometimes", "no")
                ),
                Question("What is your gender?", listOf("male", "female", "other")),
                Question(
                    "How important is the gender of your partner?",
                    listOf("not important", "important", "very important")
                ),
                Question(
                    "Do any children under the age of 18 live with you?",
                    listOf("yes", "sometimes", "no")
                )
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
