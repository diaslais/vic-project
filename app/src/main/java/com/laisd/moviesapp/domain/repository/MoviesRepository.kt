package com.laisd.moviesapp.domain.repository

import com.laisd.moviesapp.data.model.PopularMoviesResponse
import retrofit2.Call

interface MoviesRepository {
    fun getPopularMoviesResponse(apiKey: String): Call<PopularMoviesResponse>
}