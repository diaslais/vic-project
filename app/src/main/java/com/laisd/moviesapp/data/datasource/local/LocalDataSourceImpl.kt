package com.laisd.moviesapp.data.datasource.local

import com.laisd.moviesapp.data.db.MovieDao
import com.laisd.moviesapp.data.model.local.MovieEntity
import io.reactivex.rxjava3.core.Single

/**
 * This data source is responsible for communicating with database.
 *
 * The repository will communicate with this data source's interface, so it doesn't
 * know directly the frameworks used to access data.
 * **/

class LocalDataSourceImpl (private val movieDao: MovieDao): LocalDataSource {

    override fun addFavoriteMovie(movieEntity: MovieEntity) {
        movieDao.addFavoriteMovie(movieEntity)
    }

    override fun deleteFavoriteMovie(movieEntity: MovieEntity) {
        movieDao.deleteFavoriteMovie(movieEntity)
    }

    override fun getFavoriteMovies(): Single<List<MovieEntity>> = movieDao.getFavoriteMovies()

    override fun getFavoriteMovieDetail(movieId: Int): Single<MovieEntity> = movieDao.getFavoriteMovieDetail(movieId)

}