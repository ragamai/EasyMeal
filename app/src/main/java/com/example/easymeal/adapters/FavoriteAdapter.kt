package com.example.easymeal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymeal.data.Meal
import com.example.easymeal.databinding.LayoutCategoryItemBinding
import com.example.easymeal.databinding.LayoutCategoryMealBinding

class FavoriteAdapter(private val meals: List<Meal>, private val listener: OnFavoriteMealClicked): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: LayoutCategoryMealBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutCategoryMealBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.binding.tvCategory.text = meals[position].strMeal
        Glide.with(holder.binding.ivCategory)
            .load(meals[position].strMealThumb)
            .into(holder.binding.ivCategory)
        holder.itemView.setOnClickListener { listener.onMealClicked(meals[position]) }
        holder.binding.ivCategory.setOnLongClickListener {
            listener.onMealLongClicked(meals[position])
            true
        }
    }

    interface OnFavoriteMealClicked{
        fun onMealClicked(meal: Meal)
        fun onMealLongClicked(meal: Meal)
    }
}