package com.example.easymeal.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easymeal.R
import com.example.easymeal.activities.CategoryMealActivity
import com.example.easymeal.activities.MainActivity
import com.example.easymeal.adapters.CategoriesAdapter
import com.example.easymeal.data.Category
import com.example.easymeal.database.MealDatabase
import com.example.easymeal.databinding.FragmentCategoriesBinding
import com.example.easymeal.viewmodel.MealViewModelFactory
import com.example.easymeal.viewmodel.MealsViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesFragment : Fragment(), CategoriesAdapter.CategoryClicked {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var mealsViewModel: MealsViewModel
    private lateinit var categoryAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsViewModel = (activity as MainActivity).mealsViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCategories()
    }

    private fun getCategories(){
        mealsViewModel.getCategories()
        mealsViewModel.categories.observe(requireActivity()){
            categoryAdapter = CategoriesAdapter(it as ArrayList<Category>, this)
            binding.rvCategories.apply {
                layoutManager = GridLayoutManager(activity,3, GridLayoutManager.VERTICAL, false)
                adapter = categoryAdapter
            }
        }

    }

    override fun onCategoryClicked(categoryName: String) {
        val intent = Intent(activity, CategoryMealActivity::class.java)
        intent.putExtra(HomeFragment.CATEGORY_NAME, categoryName)
        startActivity(intent)
    }
}