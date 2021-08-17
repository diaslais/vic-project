package com.laisd.moviesapp.presentation.mainscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.Movie

class ViewPagerAdapter(
    private val isFavorite: (movieId: Int) -> Boolean,
    val favoriteListener: (movie: Movie) -> Unit,
    val itemClickListener: (movieId: Int) -> Unit
) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    var dataSet: MutableList<List<Movie>> = mutableListOf(listOf(), listOf())

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val recyclerView = itemView.findViewById<RecyclerView>(R.id.rvMovies)

        fun bind(list: List<Movie>) {
            val recyclerAdapter = MoviesRecyclerViewAdapter(isFavorite, favoriteListener, itemClickListener)
            recyclerView.adapter = recyclerAdapter
            val snapHelper = LinearSnapHelper()
            recyclerView.onFlingListener = null
            snapHelper.attachToRecyclerView(recyclerView)
            recyclerAdapter.movieList = list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false))

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}