package com.laisd.moviesapp.data.repository

import com.laisd.moviesapp.data.MovieMapper
import com.laisd.moviesapp.data.api.Network
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.model.MoviesList
import io.reactivex.rxjava3.core.Single

class MoviesRepository() {
    private val apiService = Network.createMoviesApiService()
    private val movieMapper = MovieMapper()

    fun getMovies(apiKey: String, language: String): Single<MoviesList> {
        val popularMoviesResponse = apiService.getPopularMoviesResponse(apiKey, language)

        return popularMoviesResponse.map {moviesListResponse ->
            movieMapper.toMoviesList(moviesListResponse)
        }
    }

    fun getMovieDetail(movieId: Int, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail> {
        val movieDetailResponse = apiService.getMovieDetailResponse(
            movieId.toString(),
            apiKey,
            language,
            appendToResponse
        )

        return movieDetailResponse.map { movieDetailResponse ->
            movieMapper.toMovieDetail(movieDetailResponse)
        }
    }

}