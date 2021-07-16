package com.laisd.moviesapp.presentation.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R

class GenresFilterAdapter() : RecyclerView.Adapter<GenresFilterAdapter.GenresFilterViewHolder>() {
    var genresList = listOf<String>()

    inner class GenresFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGenre = itemView.findViewById<TextView>(R.id.tvMovieGenre)

        fun bind(genre: String) {
            tvGenre.text = genre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresFilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_genre, parent, false
        )
        return GenresFilterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    override fun onBindViewHolder(holder: GenresFilterViewHolder, position: Int) {
        holder.bind(genresList[position])
    }
}