package com.laisd.moviesapp.presentation.mainscreen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.Movie

class ViewPagerAdapter(
    private val isFavorite: (movieId: Int) -> Boolean,
    val favoriteListener: (movie: Movie) -> Unit,
    val navigateToMovieDetails: (movieId: Int) -> Unit,
    val refreshScreen: () -> Unit
) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    var dataSet: MutableList<List<Movie>> = mutableListOf(listOf(), listOf())

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerView = itemView.findViewById<RecyclerView>(R.id.rvMovies)
        private val ivError = itemView.findViewById<ImageView>(R.id.ivMainScreenError)
        private val tvError = itemView.findViewById<TextView>(R.id.tvMainScreenErrorTitle)
        private val tvErrorDescription = itemView.findViewById<TextView>(R.id.tvMainSreenErrorDescription)
        private val btnError = itemView.findViewById<Button>(R.id.btnMainScreenErrorTryAgain)
        private val ivGreenDetail = itemView.findViewById<ImageView>(R.id.ivBtnGreenDetail)
        private val tvEmptyFavorites = itemView.findViewById<TextView>(R.id.tvEmptyFavorites)

        fun bind(list: List<Movie>, position: Int) {
            val recyclerAdapter =
                MoviesRecyclerViewAdapter(isFavorite, favoriteListener, navigateToMovieDetails)
            recyclerView.adapter = recyclerAdapter
            val snapHelper = LinearSnapHelper()
            recyclerView.onFlingListener = null
            snapHelper.attachToRecyclerView(recyclerView)

            if (position == 0 && list.isNullOrEmpty()) {
                recyclerView.visibility = View.INVISIBLE
                tvEmptyFavorites.visibility = View.GONE
                ivError.visibility = View.VISIBLE
                tvError.visibility = View.VISIBLE
                tvErrorDescription.visibility = View.VISIBLE
                btnError.visibility = View.VISIBLE
                ivGreenDetail.visibility = View.VISIBLE
                btnError.setOnClickListener {
                    notifyDataSetChanged()
                    refreshScreen()
                }
            } else if (position == 1 && list.isNullOrEmpty()) {
                tvEmptyFavorites.visibility = View.VISIBLE
            } else {
                recyclerAdapter.movieList = list
                recyclerView.visibility = View.VISIBLE
                tvEmptyFavorites.visibility = View.GONE
                ivError.visibility = View.GONE
                tvError.visibility = View.GONE
                tvErrorDescription.visibility = View.GONE
                btnError.visibility = View.GONE
                ivGreenDetail.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false)
        )

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }
}