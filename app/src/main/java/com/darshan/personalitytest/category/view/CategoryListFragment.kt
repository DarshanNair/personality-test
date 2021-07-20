package com.darshan.personalitytest.category.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.darshan.personalitytest.category.model.Category
import com.darshan.personalitytest.category.view.adapter.CategoryListAdapter
import com.darshan.personalitytest.databinding.FragmentCategoryListBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class CategoryListFragment : Fragment() {

    enum class UIState {
        LOADING,
        LOADED
    }

    private var _binding: FragmentCategoryListBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var categoryListAdapter: CategoryListAdapter

    @Inject
    lateinit var categoryListLayoutManager: RecyclerView.LayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.viewCategoryListLoaded.categoryListRecyclerView.let {
            it.layoutManager = categoryListLayoutManager
            it.adapter = categoryListAdapter
            categoryListAdapter.setOnClickListener {

            }
        }

        //TODO
        binding.viewFlipperCategoryList.displayedChild = UIState.LOADED.ordinal
        categoryListAdapter.setCategory(listOf(
            Category("hard_fact"),
            Category("lifestyle"),
            Category("introversion"),
            Category("passion")
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
