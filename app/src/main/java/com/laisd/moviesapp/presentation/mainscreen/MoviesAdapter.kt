package com.laisd.moviesapp.presentation.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.Movie

class MoviesAdapter() : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    var movieList = emptyList<Movie>()

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tvMovieTitle)

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
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
        holder.itemView.setOnClickListener { viewpagerItem ->
            viewpagerItem.findNavController().navigate(
                MainScreenFragmentDirections.actionMainScreenFragmentToMovieDetailsFragment(movie.title)
            )
        }
    }

}