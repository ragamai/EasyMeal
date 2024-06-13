package com.example.easymeal.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.easymeal.R
import com.example.easymeal.activities.CategoryMealActivity
import com.example.easymeal.activities.MainActivity
import com.example.easymeal.activities.MealActivity
import com.example.easymeal.adapters.CategoriesAdapter
import com.example.easymeal.adapters.PopularItemsAdapter
import com.example.easymeal.data.Category
import com.example.easymeal.data.Meal
import com.example.easymeal.database.MealDatabase
import com.example.easymeal.databinding.FragmentHomeBinding
import com.example.easymeal.viewmodel.MealViewModelFactory
import com.example.easymeal.viewmodel.MealsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), PopularItemsAdapter.PopularItemClicked, CategoriesAdapter.CategoryClicked{

    companion object{
        const val MEAL_ID = "MEAL_ID"
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }

    private lateinit var mealsViewModel: MealsViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter: PopularItemsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsViewModel = (activity as MainActivity).mealsViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        callRandomMeals()
        onRandomMealClicked()
        callCategories()
    }

    private fun onRandomMealClicked() {
        binding.ivRandomMeal.setOnClickListener {
            navigateToMealDetails(randomMeal.idMeal)
        }
    }

    //Navigate to Meal details Activity
    private fun navigateToMealDetails(mealId: String){
        val intent = Intent(activity,MealActivity::class.java)
        intent.putExtra(MEAL_ID, mealId)
        startActivity(intent)
    }

    private fun callRandomMeals(){
        mealsViewModel.getRandomMeals()

        mealsViewModel.randomMeals.observe(viewLifecycleOwner) { meal ->
            Log.i("randomMeal:", "${meal.idMeal}, ${meal.strCategory}")
            randomMeal = meal
            Glide.with(this)
                .load(meal.strMealThumb)
                .into(binding.ivRandomMeal)

            //call popular items
            callPopularItems()
        }
    }

    private fun callPopularItems(){
        mealsViewModel.getPopularItems(randomMeal.strCategory)
        mealsViewModel.popularItems.observe(viewLifecycleOwner){ items ->
            //set adapter
            popularItemsAdapter = PopularItemsAdapter(items as ArrayList<Meal>, this)
            //set recyclerview
            binding.rvPopularItems.apply {
                layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                adapter = popularItemsAdapter
            }
        }
    }

    private fun callCategories(){
        mealsViewModel.getCategories()
        mealsViewModel.categories.observe(viewLifecycleOwner){ categories ->
            Log.i("Categories:", categories.toString())
            //set adapter
            categoriesAdapter = CategoriesAdapter(categories as ArrayList<Category>, this)
            //set recyclerview
            binding.rvCategories.apply {
                layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL,false)
                adapter = categoriesAdapter
            }
        }
    }

    override fun onItemClicked(meal: Meal) {
        navigateToMealDetails(meal.idMeal)
    }

    override fun onCategoryClicked(categoryName: String) {
        val intent = Intent(activity, CategoryMealActivity::class.java)
        intent.putExtra(CATEGORY_NAME, categoryName)
        startActivity(intent)
    }

}