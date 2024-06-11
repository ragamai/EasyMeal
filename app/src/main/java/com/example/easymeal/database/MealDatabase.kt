package com.example.easymeal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.easymeal.data.Meal

@Database(entities = [Meal::class], version = 1)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        private var INSTANCE: MealDatabase? = null

        fun getInstance(context: Context): MealDatabase {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "Meal.db"
                ).build().also { INSTANCE = it }
            }
            return INSTANCE as MealDatabase
        }
    }

}