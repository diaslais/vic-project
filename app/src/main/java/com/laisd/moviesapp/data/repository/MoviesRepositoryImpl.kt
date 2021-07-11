package com.laisd.moviesapp.data.repository

import com.laisd.moviesapp.data.api.Network
import com.laisd.moviesapp.data.model.PopularMoviesResponse
import com.laisd.moviesapp.domain.repository.MoviesRepository
import retrofit2.Call

class MoviesRepositoryImpl() : MoviesRepository {
    private val apiService = Network.createMoviesApiService()

    override fun getPopularMoviesResponse(apiKey: String): Call<PopularMoviesResponse> {
        return apiService.getPopularMoviesList(apiKey)
    }
}