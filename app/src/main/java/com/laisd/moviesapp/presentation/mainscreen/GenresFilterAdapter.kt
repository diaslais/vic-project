package com.laisd.moviesapp.presentation.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R

class GenresFilterAdapter(
    private val genresList: List<String>
) : RecyclerView.Adapter<GenresFilterAdapter.GenresFilter>() {

    inner class GenresFilter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGenre = itemView.findViewById<TextView>(R.id.tvMovieGenre)

        fun bind(genre: String) {
            tvGenre.text = genre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresFilter {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_genre, parent, false
        )
        return GenresFilter(view)
    }

    override fun getItemCount(): Int {
        return genresList.size
    }

    override fun onBindViewHolder(holder: GenresFilter, position: Int) {
        holder.bind(genresList[position])
    }
}