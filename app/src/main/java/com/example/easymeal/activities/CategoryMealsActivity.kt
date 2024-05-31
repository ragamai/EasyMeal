package com.example.easymeal.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.easymeal.adapters.CategoryMealsAdapter
import com.example.easymeal.data.Meal
import com.example.easymeal.databinding.ActivityCategoryMealsBinding
import com.example.easymeal.fragments.HomeFragment
import com.example.easymeal.viewmodel.MealsViewModel

class CategoryMealActivity : AppCompatActivity(), CategoryMealsAdapter.CategoryMealClicked {

    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var mealsViewModel: MealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealsViewModel = ViewModelProvider(this)[MealsViewModel::class.java]
        getCategoryMeals()
    }

    private fun getCategoryMeals(){
        val categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME).toString()

        mealsViewModel.apply {
            getCategoryMeals(categoryName)
            categoryMeals.observe(this@CategoryMealActivity){ meals ->
                binding.tvCategoryName.text = "$categoryName : ${meals.size}"
                categoryMealsAdapter = CategoryMealsAdapter(meals as ArrayList<Meal>, this@CategoryMealActivity)
                binding.rvCategoryMeals.apply {
                    layoutManager = GridLayoutManager(applicationContext, 2,GridLayoutManager.VERTICAL, false)
                    adapter = categoryMealsAdapter
                }
            }
        }

    }

    override fun onCategoryMealClicked(meal: Meal) {
        val intent = Intent(this, MealActivity::class.java)
        intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
        startActivity(intent)
    }
}