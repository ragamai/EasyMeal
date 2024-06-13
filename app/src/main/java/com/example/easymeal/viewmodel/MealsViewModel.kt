package com.example.easymeal.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easymeal.data.Categories
import com.example.easymeal.data.Category
import com.example.easymeal.data.Meal
import com.example.easymeal.data.MealList
import com.example.easymeal.database.MealDatabase
import com.example.easymeal.retrofit.MealAPI
import com.example.easymeal.retrofit.RetrofitBuilder
import kotlinx.coroutines.launch
import java.lang.Exception

class MealsViewModel(val mealDatabase: MealDatabase): ViewModel() {

    //Random meal Success live data
    private val randomMealsLiveData = MutableLiveData<Meal>()
    val randomMeals: LiveData<Meal>
         get() {
             return randomMealsLiveData
         }

    //Meal Details Success live data
    private val mealDetailsLiveData = MutableLiveData<Meal>()
    val mealDetails: LiveData<Meal>
        get() {
            return mealDetailsLiveData
        }

    //Random items Success live data
    private val popularItemsLiveData = MutableLiveData<List<Meal>>()
    val popularItems: LiveData<List<Meal>>
        get() {
            return popularItemsLiveData
        }

    //Categories Success live data
    private val categoriesLiveData = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() {
            return categoriesLiveData
        }

    //Category Meals Success live data
    private val categoryMealsLiveData = MutableLiveData<List<Meal>>()
    val categoryMeals: LiveData<List<Meal>>
        get() {
            return categoryMealsLiveData
        }

    //Favorite Meals live data
    private val favoriteMealsLiveData = MutableLiveData<List<Meal>>()
    val favoriteMeals: LiveData<List<Meal>>
        get() {
            return favoriteMealsLiveData
        }

    //Search Meals live data
    private val searchMealsLiveData = MutableLiveData<List<Meal>>()
    val searchMeals: LiveData<List<Meal>>
        get() {
            return searchMealsLiveData
        }

    private val retrofitInstance: MealAPI = RetrofitBuilder.getRetrofit().create(MealAPI::class.java)

    fun getRandomMeals(){
        viewModelScope.launch {
            val randomMeal = retrofitInstance.getRandomMeal()
            try {
                if(randomMeal.isSuccessful) randomMealsLiveData.value = randomMeal.body()?.meals?.get(0)
                else Log.i("Random Meal Error", randomMeal.errorBody().toString())
            } catch (e: Exception) {
                Log.i("Random Meal Error", randomMeal.errorBody().toString())
            }
        }
    }

    fun getMealDetails(mealId: String){
        viewModelScope.launch {
            val mealDetails = retrofitInstance.getMealDetails(mealId)
            try {
                if(mealDetails.isSuccessful) mealDetailsLiveData.value = mealDetails.body()?.meals?.get(0)
                else Log.i("Meal Details Error", mealDetails.errorBody().toString())
            }catch (e: Exception){
                Log.i("Meal Details Error", mealDetails.errorBody().toString())
            }
        }
    }

    fun getPopularItems(category: String){
        viewModelScope.launch {
            val popularItems = retrofitInstance.getPopularItems(category)
            try {
                if(popularItems.isSuccessful) popularItemsLiveData.value = popularItems.body()?.meals
                else Log.i("Popular Items Error", popularItems.errorBody().toString())
            }catch (e: Exception){
                Log.i("Popular Items Error", popularItems.errorBody().toString())
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            val categories = retrofitInstance.getCategories()
            try {
                if(categories.isSuccessful) categoriesLiveData.value = categories.body()?.categories
                else Log.i("Categories Error", categories.errorBody().toString())
            }catch (e: Exception){
                Log.i("Categories Error", categories.errorBody().toString())
            }
        }
    }

    fun getCategoryMeals(category: String){
        viewModelScope.launch {
            val categoryMeals = retrofitInstance.getCategoryMeals(category)
            try {
                if(categoryMeals.isSuccessful) categoryMealsLiveData.value = categoryMeals.body()?.meals
                else Log.i("Category Meals Error", categoryMeals.errorBody().toString())
            }catch (e: Exception){
                Log.i("Category Meals Error", categoryMeals.errorBody().toString())
            }
        }
    }

    fun getSearchMeals(searchText: String){
        viewModelScope.launch {
            val searchMeals = retrofitInstance.getSearchMeals(searchText)
            try {
                if(searchMeals.isSuccessful) searchMealsLiveData.value = searchMeals.body()?.meals
                else Log.i("Search Meals Error", searchMeals.errorBody().toString())
            }catch (e: Exception){
                Log.i("Search Meals Error", searchMeals.errorBody().toString())
            }
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insertMealInfo(meal)
        }
    }

    fun getFavoriteMeal(): LiveData<List<Meal>>{
        return mealDatabase.mealDao().getMeals()
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)
        }
    }
}