package com.darshan.personalitytest.category.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.R
import com.darshan.personalitytest.category.model.Category
import com.darshan.personalitytest.databinding.ViewCategoryItemBinding
import javax.inject.Inject

class CategoryListAdapter @Inject constructor(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var clickCallback: (dealItem: Category) -> Unit
    private var categories = emptyList<Category>()

    fun setCategory(dealItems: List<Category>) {
        this.categories = dealItems
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (category: Category) -> Unit) {
        clickCallback = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ViewCategoryItemBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding).apply {
            itemView.setOnClickListener {
                categories.getOrNull(adapterPosition)?.let { clickCallback(it) }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dealsListViewHolder = holder as? CategoryViewHolder
        dealsListViewHolder?.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    override fun getItemViewType(position: Int) = R.layout.view_category_item

}