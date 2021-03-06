package com.laisd.moviesapp.presentation.moviedetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R

class MovieGenresAdapter(
    private val genres: List<String>
) : RecyclerView.Adapter<MovieGenresAdapter.MovieGenresViewHolder>() {

    inner class MovieGenresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvGenre = itemView.findViewById<TextView>(R.id.tvMovieDetailGenre)

        fun bind(genre: String) {
            tvGenre.text = genre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenresViewHolder =
        MovieGenresViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genre_detail, parent, false))

    override fun getItemCount(): Int =  genres.size

    override fun onBindViewHolder(holder: MovieGenresViewHolder, position: Int) {
        holder.bind(genres[position])
    }
}