package com.example.easymeal.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymeal.activities.MealActivity
import com.example.easymeal.data.Categories
import com.example.easymeal.data.Category
import com.example.easymeal.data.Meal
import com.example.easymeal.databinding.LayoutCategoryItemBinding
import com.example.easymeal.databinding.PopularMealItemBinding

class CategoriesAdapter(private val categories: ArrayList<Category>, private val listener: CategoryClicked):
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(val binding: LayoutCategoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(LayoutCategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        Glide.with(holder.binding.ivCategory)
            .load(categories[position].strCategoryThumb)
            .into(holder.binding.ivCategory)
        holder.binding.tvCategory.text = categories[position].strCategory

        holder.itemView.setOnClickListener {
            listener.onCategoryClicked(categories[position].strCategory)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    interface CategoryClicked {
        fun onCategoryClicked(categoryName: String)
    }
}