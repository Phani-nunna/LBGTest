package com.test.lbgcodetest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.lbgcodetest.R
import com.test.lbgcodetest.databinding.LayoutRvItemBinding
import com.test.lbgcodetest.model.Movie


class MainAdapter() : ListAdapter<Movie, MainViewHolder>(object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRvItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
}

class MainViewHolder(private val binding: LayoutRvItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
        binding.movieTitle.text = movie.title
        if (!movie.crew.isEmpty()) {
            binding.crew.text = movie.crew
        } else {
            binding.crew.text = binding.root.context.resources.getString(R.string.na)
        }

        if (!movie.imDbRating.isEmpty()) {
            binding.ratting.text =
                binding.root.context.resources.getString(R.string.rating, movie.imDbRating)
        } else {
            binding.crew.text = binding.root.context.resources.getString(R.string.na)
        }


        if (!movie.imDbRatingCount.isEmpty()) {
            binding.rattingCount.text = binding.root.context.resources.getString(
                R.string.ratingCount,
                movie.imDbRatingCount
            )
        } else {
            binding.crew.text = binding.root.context.resources.getString(R.string.na)
        }
        if (!movie.year.isEmpty()) {
            binding.year.text = binding.root.context.resources.getString(R.string.year, movie.year)
        } else {
            binding.crew.text = binding.root.context.resources.getString(R.string.na)
        }

        Glide.with(itemView.context).load(movie.image).placeholder(R.drawable.placeholder)
            .into(binding.moviePoster)
    }
}