package com.darshan.personalitytest.category.view.adapter

import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.category.model.Category
import com.darshan.personalitytest.databinding.ViewCategoryItemBinding

class CategoryViewHolder(
    private val binding: ViewCategoryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category) {
        binding.categoryTitle.text = category.name
    }

}