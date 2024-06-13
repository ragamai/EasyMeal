package com.example.easymeal.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easymeal.R
import com.example.easymeal.activities.MainActivity
import com.example.easymeal.activities.MealActivity
import com.example.easymeal.adapters.CategoryMealsAdapter
import com.example.easymeal.data.Meal
import com.example.easymeal.databinding.FragmentSearchBinding
import com.example.easymeal.viewmodel.MealsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment(), CategoryMealsAdapter.CategoryMealClicked {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mealsViewModel: MealsViewModel
    private lateinit var searchAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsViewModel = (activity as MainActivity).mealsViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchText = binding.etSearch.text.toString()
        binding.ivSearch.setOnClickListener {
            mealsViewModel.getSearchMeals(searchText)

            mealsViewModel.searchMeals.observe(requireActivity()){
                binding.rvSearchList.apply {
                    layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
                    adapter = CategoryMealsAdapter(it as ArrayList<Meal>, this@SearchFragment)
                }
            }
        }
    }

    override fun onCategoryMealClicked(meal: Meal) {
        val intent = Intent(requireActivity(), MealActivity::class.java)
        intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
        startActivity(intent)
    }
}