package com.laisd.moviesapp.data.repository

import com.laisd.moviesapp.data.MovieMapper
import com.laisd.moviesapp.data.api.Network
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.model.MoviesList
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

/**
 * This repository is responsible for fetching
 * data from server or db
 * **/

class MovieRepositoryImpl(private val movieMapper: MovieMapper) : MovieRepository {
    private val apiService = Network.createMoviesApiService()

    override fun getMovies(apiKey: String, language: String): Single<MoviesList> {
        val popularMoviesResponse = apiService.getPopularMoviesResponse(apiKey, language)

        return popularMoviesResponse.map {moviesListResponse ->
            movieMapper.toMoviesList(moviesListResponse)
        }
    }
}