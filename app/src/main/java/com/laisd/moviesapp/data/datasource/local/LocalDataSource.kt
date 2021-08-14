package com.laisd.moviesapp.data.datasource.local

import com.laisd.moviesapp.data.model.local.MovieEntity
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {

    fun addFavoriteMovie(movieEntity: MovieEntity)

    fun deleteFavoriteMovie(movieEntity: MovieEntity)

    fun getFavoriteMovies(): Single<List<MovieEntity>>

    fun searchMovie(movieId: Int): Single<MovieEntity>
}