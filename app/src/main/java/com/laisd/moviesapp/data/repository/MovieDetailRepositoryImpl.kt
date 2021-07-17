package com.laisd.moviesapp.data.repository

import com.laisd.moviesapp.data.MovieMapper
import com.laisd.moviesapp.data.api.Network
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.MovieDetailRepository
import io.reactivex.rxjava3.core.Single

class MovieDetailRepositoryImpl (private val movieMapper: MovieMapper): MovieDetailRepository {
    private val apiService = Network.createMoviesApiService()

    override fun getMovieDetail(movieId: Int, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail> {
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