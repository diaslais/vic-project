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
    val regularLists: () -> Unit,
    val genreListener: (genre: String) -> Unit
) : RecyclerView.Adapter<GenresFilterAdapter.GenresFilterViewHolder>() {
    var genresList = listOf<String>()
    private var selectedGenrePosition: Int? = null

    inner class GenresFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnGenre = itemView.findViewById<TextView>(R.id.btnMovieGenre)

        fun bind(genre: String) { btnGenre.text = genre }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresFilterViewHolder =
        GenresFilterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false))

    override fun getItemCount(): Int = genresList.size

    override fun onBindViewHolder(holder: GenresFilterViewHolder, position: Int) {
        holder.bind(genresList[position])
        val genre = genresList[position]

        var isSelected: Boolean

        if (selectedGenrePosition == position) {
            holder.btnGenre.setBackgroundResource(R.drawable.genre_selected)
            holder.btnGenre.setTextColor(getColor(context, R.color.light_gray))
            genreListener(genre)
            isSelected = true
        } else {
            holder.btnGenre.setBackgroundResource(R.drawable.genre_background_gray)
            holder.btnGenre.setTextColor(getColor(context, R.color.gray3))
            isSelected = false
        }

        holder.btnGenre.setOnClickListener {
            if (isSelected) {
                it.setBackgroundResource(R.drawable.genre_background_gray)
                (it as Button).setTextColor(getColor(context, R.color.gray3))
                regularLists()
                isSelected = false
            } else {
                it.setBackgroundResource(R.drawable.genre_selected)
                (it as Button).setTextColor(getColor(context, R.color.light_gray))
                genreListener(genre)
                isSelected = true
            }
            if (selectedGenrePosition != position) {
                selectedGenrePosition?.let { notifyItemChanged(it) }
                selectedGenrePosition = position
            }
        }
    }
}