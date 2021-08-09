package com.laisd.moviesapp.presentation.mainscreen.adapters

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.Movie

class ViewPagerAdapter(val viewpager: ViewPager2, val clickListener: (movieId: Int) -> Unit) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    var dataSet: MutableList<List<Movie>> = mutableListOf(listOf(), listOf())

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.rvMovies)

        fun bind(list: List<Movie>) {
            val recyclerAdapter = MoviesRecyclerViewAdapter(clickListener)
            recyclerView.adapter = recyclerAdapter
            recyclerAdapter.movieList = list
        }

        fun handleNestedScroll() {
            recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                var initialTouchPosition = 0
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.action) {
                        MotionEvent.ACTION_DOWN -> initialTouchPosition = e.x.toInt()
                        MotionEvent.ACTION_MOVE -> {
                            val isScrollingRight = e.x < initialTouchPosition
                            val isScrollingLeft = e.x > initialTouchPosition

                            val isRecyclerViewsLastItem =
                                (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == recyclerView.adapter!!.itemCount - 1
                            val isRecyclerViewsFirstItem =
                                (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0

                            viewpager.isUserInputEnabled =
                                (isScrollingRight && isRecyclerViewsLastItem) || (isScrollingLeft && isRecyclerViewsFirstItem)
                        }
                        MotionEvent.ACTION_UP -> {
                            initialTouchPosition = 0
                            viewpager.isUserInputEnabled = true
                        }
                    }
                    return false
                }
                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder =
        ViewPagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager, parent, false))

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(dataSet[position])
        holder.handleNestedScroll()
    }
}