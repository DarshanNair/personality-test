package com.darshan.personalitytest.question.view.adapter

import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.databinding.ViewQuestionItemBinding
import com.darshan.personalitytest.question.model.Question

class QuestionsViewHolder(
    private val binding: ViewQuestionItemBinding,
    private val clickCallback: (question: Question) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(question: Question) {
        binding.question.text = question.question
        with(binding.radioContent) {
            removeAllViewsInLayout()
            addView(createOptionsView(question))
        }
    }

    private fun createOptionsView(question: Question): View {
        val radioGroup = RadioGroup(binding.root.context)
        question.options.forEach {
            val radioButton = RadioButton(binding.root.context)
            radioButton.text = it
            radioButton.tag = it
            radioGroup.addView(radioButton)
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group?.findViewById<RadioButton>(checkedId)
            radioButton?.let {
                question.selectedOption = it.text.toString()
                clickCallback(question)
            }
        }
        return radioGroup
    }

}