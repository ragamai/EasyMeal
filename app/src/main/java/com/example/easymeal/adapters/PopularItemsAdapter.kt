package com.example.easymeal.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easymeal.activities.MealActivity
import com.example.easymeal.data.Meal
import com.example.easymeal.databinding.PopularMealItemBinding

class PopularItemsAdapter(private val popularItems: ArrayList<Meal>, val listener: PopularItemClicked):
    RecyclerView.Adapter<PopularItemsAdapter.PopularItemViewHolder>() {

    class PopularItemViewHolder(val binding: PopularMealItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder {
        return PopularItemViewHolder(PopularMealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(popularItems[position].strMealThumb)
            .into(holder.binding.ivPopularItem)

        holder.itemView.setOnClickListener {
            listener.onItemClicked(popularItems[position])
        }
    }

    override fun getItemCount(): Int {
        return popularItems.size
    }

    interface PopularItemClicked {
        fun onItemClicked(meal: Meal)
    }
}