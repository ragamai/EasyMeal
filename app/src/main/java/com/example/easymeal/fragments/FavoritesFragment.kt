package com.example.easymeal.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easymeal.R
import com.example.easymeal.activities.MainActivity
import com.example.easymeal.activities.MealActivity
import com.example.easymeal.adapters.FavoriteAdapter
import com.example.easymeal.data.Meal
import com.example.easymeal.database.MealDatabase
import com.example.easymeal.databinding.FragmentFavoritesBinding
import com.example.easymeal.viewmodel.MealViewModelFactory
import com.example.easymeal.viewmodel.MealsViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritesFragment : Fragment(), FavoriteAdapter.OnFavoriteMealClicked {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var mealsViewModel: MealsViewModel
    private lateinit var favMealsAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mealsViewModel = (activity as MainActivity).mealsViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getFavoriteMeals()
    }

    private fun getFavoriteMeals(){
        mealsViewModel.getFavoriteMeal().observe(requireActivity()){
            favMealsAdapter = FavoriteAdapter(it, this)
            binding.rvFavoriteMeals.apply {
                layoutManager = GridLayoutManager(activity, 2,GridLayoutManager.VERTICAL, false)
                adapter = favMealsAdapter
            }
        }
    }

    override fun onMealClicked(meal: Meal) {
        val intent = Intent(requireActivity(), MealActivity::class.java)
        intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
        startActivity(intent)
    }

    override fun onMealLongClicked(meal: Meal) {
        mealsViewModel.deleteMeal(meal)
        Snackbar.make(requireView(), "Undo the Meal", Snackbar.LENGTH_LONG)
            .setAction("Undo", View.OnClickListener {
                mealsViewModel.insertMeal(meal)
            }).show()
    }

}