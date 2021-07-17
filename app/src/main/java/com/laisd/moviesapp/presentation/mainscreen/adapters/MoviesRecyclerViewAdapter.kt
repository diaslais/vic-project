package com.laisd.moviesapp.presentation.mainscreen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.presentation.ItemListener

class MoviesRecyclerViewAdapter(private val clickListener: ItemListener) :
    RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder>() {
    var movieList = emptyList<Movie>()

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"

        val tvTitle = itemView.findViewById<TextView>(R.id.tvMovieTitle)
        val ivPoster = itemView.findViewById<ImageView>(R.id.ivMovieImage)
        val tvRating = itemView.findViewById<TextView>(R.id.tvRating)

        fun bind(movie: Movie) {
            var pictureUrl: String? = null
            movie.poster?.let {
                pictureUrl = imageBaseUrl + it
            }

            tvTitle.text = movie.title
            loadImage(pictureUrl, ivPoster)
            tvRating.text = movie.userRating.toString()
        }

        private fun loadImage(url: String?, imageView: ImageView) {
            Glide.with(itemView)
                .load(url)
                .fallback(R.drawable.drive)
                .centerCrop()
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            clickListener.onClick(movieList[position].id)
        }
    }
}