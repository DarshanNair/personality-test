package com.darshan.personalitytest.question.view.adapter

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.databinding.ViewQuestionItemBinding
import com.darshan.personalitytest.question.model.Question

class QuestionsViewHolder(
    private val binding: ViewQuestionItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(question: Question) {
        binding.question.text = question.name
        with(binding.radioContent) {
            removeAllViewsInLayout()
            addView(createOptionsView(question.options))
        }
    }

    private fun createOptionsView(options: List<String>): View {
        val radioGroup = RadioGroup(binding.root.context)
        options.forEach {
            val radioButton = RadioButton(binding.root.context)
            radioButton.text = it
            radioGroup.addView(radioButton)
        }
        return radioGroup
    }

}