package com.example.easymeal.retrofit

import com.example.easymeal.data.Categories
import com.example.easymeal.data.Meal
import com.example.easymeal.data.MealList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealAPI {

    @GET("random.php")
    suspend fun getRandomMeal() : Response<MealList>

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") id: String): Response<MealList>

    @GET("filter.php")
    suspend fun getPopularItems(@Query("c") category: String): Response<MealList>

    @GET("categories.php")
    suspend fun getCategories(): Response<Categories>

    @GET("filter.php")
    suspend fun getCategoryMeals(@Query("c") category: String): Response<MealList>

    @GET("search.php")
    suspend fun getSearchMeals(@Query("s") mealName: String): Response<MealList>
}