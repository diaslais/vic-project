package com.laisd.moviesapp.presentation.mainscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R

class GenresFilterAdapter(
    val genreListener: (genre: String) -> Unit
) : RecyclerView.Adapter<GenresFilterAdapter.GenresFilterViewHolder>() {
    var genresList = listOf<String>()

    inner class GenresFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnGenre = itemView.findViewById<TextView>(R.id.btnMovieGenre)

        fun bind(genre: String) {
            btnGenre.text = genre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresFilterViewHolder =
        GenresFilterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false))

    override fun getItemCount(): Int = genresList.size

    override fun onBindViewHolder(holder: GenresFilterViewHolder, position: Int) {
        holder.bind(genresList[position])
        val genre = genresList[position]

        holder.btnGenre.setOnClickListener {
            println("CLICOU NO GENERO $genre")
            genreListener(genre)
        }
    }
}