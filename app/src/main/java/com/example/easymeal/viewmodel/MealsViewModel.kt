package com.example.easymeal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easymeal.data.Meal
import com.example.easymeal.data.MealList
import com.example.easymeal.retrofit.MealAPI
import com.example.easymeal.retrofit.RetrofitBuilder
import kotlinx.coroutines.launch
import java.lang.Exception

class MealsViewModel: ViewModel() {

    private val randomMealsLiveData = MutableLiveData<Meal>()
    val randomMeals: LiveData<Meal>
         get() {
             return randomMealsLiveData
         }

    private val errorRandomMealsLiveData = MutableLiveData<String>()
    val errorRandomMeals: LiveData<String>
         get() {
             return errorRandomMealsLiveData
         }

    private val mealDetailsLiveData = MutableLiveData<Meal>()
    val mealDetails: LiveData<Meal>
        get() {
            return mealDetailsLiveData
        }

    private val errorMealDetailsLiveData = MutableLiveData<String>()
    val errorMealDetails: LiveData<String>
        get() {
            return errorMealDetailsLiveData
        }

    private val retrofitInstance: MealAPI = RetrofitBuilder.getRetrofit().create(MealAPI::class.java)

    fun getRandomMeals(){
        viewModelScope.launch {
            val randomMeal = retrofitInstance.getRandomMeal()
            try {
                if(randomMeal.isSuccessful) randomMealsLiveData.value = randomMeal.body()?.meals?.get(0)
                else errorRandomMealsLiveData.value = randomMeal.errorBody().toString()
            } catch (e: Exception) {
                errorRandomMealsLiveData.value = e.message.toString()
            }

        }
    }

    fun getMealDetails(mealId: String){
        viewModelScope.launch {
            val mealDetails = retrofitInstance.getMealDetails(mealId)
            try {
                if(mealDetails.isSuccessful) mealDetailsLiveData.value = mealDetails.body()?.meals?.get(0)
                else errorMealDetailsLiveData.value = mealDetails.errorBody().toString()
            }catch (e: Exception){
                errorMealDetailsLiveData.value = e.message.toString()
            }
        }
    }
}