package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.data.model.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidRadaItemBinding

class AsteroidRadarAdapter(
    private val clickListener: AsteroidListener
) :
    ListAdapter<Asteroid, AsteroidRadarAdapter.AsteroidRadarViewHolder>(AsteroidDiffCallback()) {

    inner class AsteroidRadarViewHolder(private val binding: AsteroidRadaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Asteroid, clickListener: AsteroidListener) {
            binding.asteroid = item
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidRadarViewHolder =
        AsteroidRadarViewHolder(
            AsteroidRadaItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: AsteroidRadarViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }
    }
    class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}