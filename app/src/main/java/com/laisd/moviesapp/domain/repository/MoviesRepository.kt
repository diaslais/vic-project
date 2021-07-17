package com.laisd.moviesapp.domain.repository

import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.model.MoviesList
import io.reactivex.rxjava3.core.Single

/**
 * This interface exists to make an interaction
 * between the domain layer (specifically usecases)
 * and the data layer (repositories implementations)
 *
 * Dependency Inversion Principle - our code should depend on abstractions
 * That way we decouple our code from the lower level implementations
 * **/

interface MoviesRepository {

    fun getMovies(apiKey: String, language: String): Single<MoviesList>

    fun getMovieDetail(movieId: Int, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail>

}