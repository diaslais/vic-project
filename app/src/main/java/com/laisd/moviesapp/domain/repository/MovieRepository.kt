package com.laisd.moviesapp.domain.repository

import com.laisd.moviesapp.domain.model.Genre
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import io.reactivex.rxjava3.core.Single

interface MovieRepository {

    fun getMovies(apiKey: String, language: String): Single<List<Movie>>

    fun getMovieDetail(movieId: Int, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail>

    fun getGenres(apiKey: String, language: String): Single<List<Genre>>

    fun getMoviesByGenre(apiKey: String, language: String, genres: String): Single<List<Movie>>

    fun searchMovie(apiKey: String, language: String, query: String): Single<List<Movie>>

}