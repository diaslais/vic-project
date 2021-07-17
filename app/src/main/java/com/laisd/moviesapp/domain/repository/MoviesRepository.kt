package com.laisd.moviesapp.domain.repository

import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.model.MoviesList
import io.reactivex.rxjava3.core.Single

interface MoviesRepository {

    fun getMovies(apiKey: String, language: String): Single<MoviesList>

    fun getMovieDetail(movieId: Int, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail>

}