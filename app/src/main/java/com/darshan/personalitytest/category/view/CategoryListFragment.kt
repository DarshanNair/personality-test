package com.darshan.personalitytest.category.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.darshan.core.DeviceManager
import com.darshan.personalitytest.R
import com.darshan.personalitytest.category.view.adapter.CategoryListAdapter
import com.darshan.personalitytest.category.viewmodel.CategoryListViewModel
import com.darshan.coretesting.EspressoIdlingResource
import com.darshan.personalitytest.databinding.FragmentCategoryListBinding
import com.darshan.personalitytest.main.viewmodel.SharedViewModel
import com.darshan.personalitytest.question.view.QuestionsFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class CategoryListFragment : Fragment() {

    enum class UIState {
        LOADING,
        LOADED,
        ERROR,
        EMPTY
    }

    private var _binding: FragmentCategoryListBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var categoryListAdapter: CategoryListAdapter

    @Inject
    lateinit var categoryListLayoutManager: RecyclerView.LayoutManager

    @Inject
    lateinit var categoryListViewModel: CategoryListViewModel

    @Inject
    lateinit var sharedViewModel: SharedViewModel

    @Inject
    lateinit var deviceManager: DeviceManager

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupClickListener()
        setupViewModel(savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.viewCategoryListLoaded.categoryListRecyclerView.let {
            it.layoutManager = categoryListLayoutManager
            it.adapter = categoryListAdapter
        }
    }

    private fun setupClickListener() {
        binding.viewGeneralError.tryAgainButton.setOnClickListener {
            categoryListViewModel.loadCategories()
        }
        categoryListAdapter.setOnClickListener { category ->
            sharedViewModel.state.value = category.name
            if (!deviceManager.isTablet) {
                requireActivity().supportFragmentManager.commit {
                    addToBackStack(null)
                    add(R.id.container, QuestionsFragment())
                }
            }
        }
    }

    private fun setupViewModel(savedInstanceState: Bundle?) {
        categoryListViewModel.apply {
            state.observe(viewLifecycleOwner, { it?.let { onCategoryLoaded(it) } })
            if (savedInstanceState == null) {
                EspressoIdlingResource.increment()
                sharedViewModel.state.value = null
                loadCategories()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCategoryLoaded(state: CategoryListViewModel.State) {
        when (state) {
            CategoryListViewModel.State.Loading -> {
                binding.viewFlipperCategoryList.displayedChild = UIState.LOADING.ordinal
            }
            is CategoryListViewModel.State.Success -> {
                binding.viewFlipperCategoryList.displayedChild = UIState.LOADED.ordinal
                categoryListAdapter.setCategory(state.categories)
                EspressoIdlingResource.decrement()
            }
            CategoryListViewModel.State.Error -> {
                binding.viewFlipperCategoryList.displayedChild = UIState.ERROR.ordinal
                EspressoIdlingResource.decrement()
            }
            CategoryListViewModel.State.Empty -> {
                EspressoIdlingResource.decrement()
                //TODO
            }
        }
    }

}
