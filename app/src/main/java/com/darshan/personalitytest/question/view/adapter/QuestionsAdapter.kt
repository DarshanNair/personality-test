package com.darshan.personalitytest.question.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.R
import com.darshan.personalitytest.databinding.ViewQuestionItemBinding
import com.darshan.personalitytest.question.model.Question
import javax.inject.Inject

class QuestionsAdapter @Inject constructor(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<QuestionsViewHolder>() {

    private lateinit var clickCallback: (question: Question) -> Unit
    private var questions = emptyList<Question>()

    fun setQuestion(questions: List<Question>) {
        this.questions = questions
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (questions: Question) -> Unit) {
        clickCallback = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
        val binding = ViewQuestionItemBinding.inflate(layoutInflater, parent, false)
        return QuestionsViewHolder(binding, clickCallback)
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount() = questions.size

    override fun getItemViewType(position: Int) = R.layout.view_question_item

}