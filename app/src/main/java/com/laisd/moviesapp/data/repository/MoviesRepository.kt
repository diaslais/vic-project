package com.laisd.moviesapp.data.repository

import com.laisd.moviesapp.data.MovieMapper
import com.laisd.moviesapp.data.api.Network
import com.laisd.moviesapp.domain.model.MoviesList
import io.reactivex.rxjava3.core.Single

class MoviesRepository() {
    private val apiService = Network.createMoviesApiService()
    private val movieMapper = MovieMapper()

    fun getPopularMovies(apiKey: String): Single<MoviesList> {
        val popularMoviesResponse = apiService.getPopularMoviesResponse(apiKey)

        return popularMoviesResponse.map {
            movieMapper.toPopularMovies(it)
        }
    }
}