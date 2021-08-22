package com.laisd.moviesapp.data.repository

import com.laisd.moviesapp.data.datasource.local.LocalDataSource
import com.laisd.moviesapp.data.mapper.FavoriteMapper
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.FavoritesRepository
import io.reactivex.rxjava3.core.Single

/**
 * This repository is responsible for fetching data from local data source and return
 * mapped data.
 *
 * It's the implementation of the FavoritesRepository interface (from domain layer), which connects the
 * data layer and the domain layer.
 * **/

class FavoritesRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val favoriteMapper: FavoriteMapper
): FavoritesRepository {

    override fun addFavoriteMovie(movie: Movie, movieDetail: MovieDetail) {
        localDataSource.addFavoriteMovie(favoriteMapper.mapToEntity(movie, movieDetail))
    }

    override fun deleteFavoriteMovie(movie: Movie, movieDetail: MovieDetail) {
        localDataSource.deleteFavoriteMovie(favoriteMapper.mapToEntity(movie, movieDetail))
    }

    override fun getFavoriteMovies(): Single<List<Movie>> =
        localDataSource.getFavoriteMovies().map(favoriteMapper::mapFromEntityToMovie)

    override fun getFavoriteMovieDetail(movieId: Int): Single<MovieDetail> =
        localDataSource.getFavoriteMovieDetail(movieId).map(favoriteMapper::mapFromEntityToMovieDetail)

}