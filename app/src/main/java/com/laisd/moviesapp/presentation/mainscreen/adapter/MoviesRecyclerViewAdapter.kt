package com.laisd.moviesapp.presentation.mainscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.Movie

class MoviesRecyclerViewAdapter(
    private val isFavorite: (movieId: Int) -> Boolean,
    private val favoriteListener: (movie: Movie) -> Unit,
    private val clickListener: (movieId: Int) -> Unit
) :
    RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder>() {
    var movieList = emptyList<Movie>()

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"

        val tvTitle = itemView.findViewById<TextView>(R.id.tvMovieTitle)
        val ivPoster = itemView.findViewById<ImageView>(R.id.ivMovieImage)
        val tvRating = itemView.findViewById<TextView>(R.id.tvRating)
        val ibFavorite = itemView.findViewById<ImageButton>(R.id.ibFavorite)

        fun bind(movie: Movie) {
            var pictureUrl: String? = null
            movie.poster?.let {
                pictureUrl = imageBaseUrl + it
            }

            tvTitle.text = movie.title
            loadImage(pictureUrl, ivPoster)
            tvRating.text = movie.userRating.toString()

            if (isFavorite(movie.id)) {
                ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                ibFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        private fun loadImage(url: String?, imageView: ImageView) {
            Glide.with(itemView)
                .load(url)
                .fallback(R.drawable.drive)
                .centerCrop()
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder =
        MoviesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            clickListener(movieList[position].id)
        }

        holder.ibFavorite.setOnClickListener {
            favoriteListener(movie)
        }
    }
}