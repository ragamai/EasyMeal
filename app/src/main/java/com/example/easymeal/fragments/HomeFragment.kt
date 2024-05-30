package com.example.easymeal.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easymeal.R
import com.example.easymeal.activities.MainActivity
import com.example.easymeal.activities.MealActivity
import com.example.easymeal.data.Meal
import com.example.easymeal.databinding.FragmentHomeBinding
import com.example.easymeal.viewmodel.MealsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    companion object{
        const val MEAL_ID = "MEAL_ID"
    }

    private lateinit var mealsViewModel: MealsViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var randomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsViewModel = ViewModelProvider(this)[MealsViewModel::class.java]
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

        callRandomMeals()
        onRandomMealClicked()
    }

    private fun onRandomMealClicked() {
        binding.ivRandomMeal.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            startActivity(intent)
        }
    }

    private fun callRandomMeals(){
        mealsViewModel.getRandomMeals()

        this.activity?.let {
            mealsViewModel.randomMeals.observe(it) { meal ->
                Log.i("randomMeal:", "${meal.idMeal}, ${meal.strCategory}")
                randomMeal = meal
                Glide.with(this)
                    .load(meal.strMealThumb)
                    .into(binding.ivRandomMeal)
            }
        }
    }

}