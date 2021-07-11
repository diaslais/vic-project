package com.laisd.moviesapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laisd.moviesapp.data.model.MovieResponse
import com.laisd.moviesapp.data.model.PopularMoviesResponse
import com.laisd.moviesapp.domain.usecase.GetMoviesUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel() : ViewModel() {
    private val useCase = GetMoviesUseCase()

    init {
        setPopularMovies()
    }

    private val _genresList = MutableLiveData<List<String>>(
        arrayListOf(
            "Ação",
            "Anime",
            "Comédia",
            "Drama",
            "Terror",
            "Antigos",
            "Suspense"
        )
    )
    val genresList: LiveData<List<String>>
        get() = _genresList


    private val _moviesList = MutableLiveData<List<MovieResponse>>()

    val moviesList: LiveData<List<MovieResponse>>
        get() = _moviesList

    private fun setPopularMovies() {
        val service = useCase.execute()
        service.enqueue(object : Callback<PopularMoviesResponse> {
            override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable) {
                Log.e("error", "error")
            }

            override fun onResponse(
                call: Call<PopularMoviesResponse>,
                response: Response<PopularMoviesResponse>
            ) {
                val popularMoviesList = response.body()
                if (popularMoviesList != null) {
                    _moviesList.value = popularMoviesList.results
                } else {
                    _moviesList.value = listOf(MovieResponse("foi nulo"))
                }
            }
        })
    }
}