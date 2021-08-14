package com.laisd.moviesapp.data.datasource.local

import com.laisd.moviesapp.data.db.MovieDao
import com.laisd.moviesapp.data.model.local.MovieEntity
import io.reactivex.rxjava3.core.Single

class LocalDataSourceImpl (private val movieDao: MovieDao): LocalDataSource {

    override fun addFavoriteMovie(movieEntity: MovieEntity) {
        movieDao.addFavoriteMovie(movieEntity)
    }

    override fun deleteFavoriteMovie(movieEntity: MovieEntity) {
        movieDao.deleteFavoriteMovie(movieEntity)
    }

    override fun getFavoriteMovies(): Single<List<MovieEntity>> = movieDao.getFavoriteMovies()

    override fun searchMovie(movieId: Int): Single<MovieEntity> = movieDao.searchMovie(movieId)

}