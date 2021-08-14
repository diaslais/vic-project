package com.laisd.moviesapp.domain.repository

import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import io.reactivex.rxjava3.core.Single

interface FavoritesRepository {

    fun addFavoriteMovie(movie: Movie, movieDetail: MovieDetail)

    fun deleteFavoriteMovie(movie: Movie, movieDetail: MovieDetail)

    fun getFavoriteMovies(): Single<List<Movie>>

    fun getFavoriteMovieDetail(movieId: Int): Single<MovieDetail>
}