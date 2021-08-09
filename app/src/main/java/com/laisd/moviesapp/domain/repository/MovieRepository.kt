package com.laisd.moviesapp.domain.repository

import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import io.reactivex.rxjava3.core.Single

interface MovieRepository {

    fun getMovies(apiKey: String, language: String): Single<List<Movie>>

    fun getMovieDetail(movieId: Int, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail>

}