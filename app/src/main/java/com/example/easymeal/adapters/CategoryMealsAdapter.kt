package com.example.easymeal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymeal.data.Meal
import com.example.easymeal.databinding.LayoutCategoryMealBinding

class CategoryMealsAdapter(private val categories: ArrayList<Meal>, private val listener: CategoryMealClicked):
    RecyclerView.Adapter<CategoryMealsAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(val binding: LayoutCategoryMealBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(LayoutCategoryMealBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.binding.ivCategory)
            .load(categories[position].strMealThumb)
            .into(holder.binding.ivCategory)
        holder.binding.tvCategory.text = categories[position].strMeal

        holder.itemView.setOnClickListener {
            listener.onCategoryMealClicked(categories[position])
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    interface CategoryMealClicked {
        fun onCategoryMealClicked(meal: Meal)
    }
}