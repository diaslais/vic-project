package com.laisd.moviesapp.presentation.mainscreen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R

class GenresFilterAdapter(
    val context: Context,
    var positionQueVemDoFragment: Int?,
    val genreListener: (genre: String?, position: Int?) -> Unit
) : RecyclerView.Adapter<GenresFilterAdapter.GenresFilterViewHolder>() {
    var genresList = listOf<String>()
    var currentGenrePosition: Int? = positionQueVemDoFragment

    inner class GenresFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnGenre = itemView.findViewById<Button>(R.id.btnMovieGenre)

        fun bind(genre: String) {
            btnGenre.text = genre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresFilterViewHolder =
        GenresFilterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        )

    override fun getItemCount(): Int = genresList.size

    override fun onBindViewHolder(holder: GenresFilterViewHolder, position: Int) {
        holder.bind(genresList[position])
        var genre: String? = genresList[position]

        var isSelected: Boolean

        if (currentGenrePosition == position) {
            holder.btnGenre.setBackgroundResource(R.drawable.genre_selected)
            holder.btnGenre.setTextColor(getColor(context, R.color.light_gray))
            genreListener(genre, position)
            isSelected = true
        } else {
            holder.btnGenre.setBackgroundResource(R.drawable.genre_background_gray)
            holder.btnGenre.setTextColor(getColor(context, R.color.gray3))
            isSelected = false
        }

        holder.btnGenre.setOnClickListener { btnGenre ->
            if (currentGenrePosition != position) {
                currentGenrePosition?.let { notifyItemChanged(it) }
                currentGenrePosition = position
            }
            if (isSelected) {
                btnGenre.setBackgroundResource(R.drawable.genre_background_gray)
                (btnGenre as Button).setTextColor(getColor(context, R.color.gray3))
                genre = null
                genreListener(genre, position)
                isSelected = false
                currentGenrePosition = null
            } else {
                btnGenre.setBackgroundResource(R.drawable.genre_selected)
                (btnGenre as Button).setTextColor(getColor(context, R.color.light_gray))
                genre = genresList[position]
                genreListener(genre, position)
                isSelected = true
            }
        }
    }
}