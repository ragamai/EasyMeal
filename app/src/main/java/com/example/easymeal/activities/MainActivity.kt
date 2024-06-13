package com.example.easymeal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.easymeal.R
import com.example.easymeal.database.MealDatabase
import com.example.easymeal.databinding.ActivityMainBinding
import com.example.easymeal.viewmodel.MealViewModelFactory
import com.example.easymeal.viewmodel.MealsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var mealsViewModel: MealsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        val database = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(database)
        mealsViewModel = ViewModelProvider(this, viewModelFactory)[MealsViewModel::class.java]
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(binding.btmNav,navController)
    }
}