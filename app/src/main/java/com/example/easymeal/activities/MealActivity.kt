package com.example.easymeal.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easymeal.R
import com.example.easymeal.data.Meal
import com.example.easymeal.databinding.ActivityMealBinding
import com.example.easymeal.fragments.HomeFragment
import com.example.easymeal.viewmodel.MealsViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var viewModel: MealsViewModel
    private lateinit var mealId: String
    private lateinit var meal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealId = this.intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        getMealDetails()
        setVideoOnClick()
    }

    private fun getMealDetails(){
        viewModel = ViewModelProvider(this)[MealsViewModel::class.java]
        viewModel.getMealDetails(mealId)
        viewModel.mealDetails.observe(this){
            setMealDetails(it)
        }
    }

    private fun setMealDetails(meal: Meal){
        this.meal = meal
        Glide.with(this)
            .load(meal.strMealThumb)
            .into(binding.ivMeal)

        binding.collapsingToolbar.title = meal.strMeal
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.tvCategoryName.text = "Category: ${meal.strCategory}"
        binding.tvArea.text = "Area: ${meal.strArea}"
        binding.tvInstructions.text = meal.strInstructions
    }

    private fun setVideoOnClick(){
        binding.fabVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube))
            startActivity(intent)
        }
    }
}