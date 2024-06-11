package com.example.easymeal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MealInfo")
data class Meal(
    @PrimaryKey
    val idMeal: String,
    val strArea: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealThumb: String,
    val strSource: String,
    val strYoutube: String
)