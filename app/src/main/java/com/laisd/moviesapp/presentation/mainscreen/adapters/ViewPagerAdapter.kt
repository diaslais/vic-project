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
import com.laisd.moviesapp.presentation.ItemListener

class ViewPagerAdapter(val clickListener: ItemListener, val viewpager: ViewPager2) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerVH>() {
    var dataSet: MutableList<List<Movie>> = mutableListOf(listOf(), listOf())

    inner class ViewPagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerview = itemView.findViewById<RecyclerView>(R.id.rvMovies)

        fun bind(list: List<Movie>) {
            val recyclerAdapter =
                MoviesRecyclerViewAdapter(
                    clickListener
                )
            recyclerview.adapter = recyclerAdapter
            recyclerAdapter.movieList = list
        }

        fun handleNestedScroll() {
            recyclerview.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                var initialTouchPosition = 0
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.action) {
                        MotionEvent.ACTION_DOWN -> initialTouchPosition = e.x.toInt()
                        MotionEvent.ACTION_MOVE -> {
                            val isScrollingRight = e.x < initialTouchPosition
                            val isScrollingLeft = e.x > initialTouchPosition

                            val isRecyclerViewsLastItem =
                                (recyclerview.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == recyclerview.adapter!!.itemCount - 1
                            val isRecyclerViewsFirstItem =
                                (recyclerview.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerVH {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_viewpager, parent, false
        )
        return ViewPagerVH(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewPagerVH, position: Int) {
        holder.bind(dataSet[position])
        holder.handleNestedScroll()
    }
}