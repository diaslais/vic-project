package com.laisd.moviesapp.data.repository

import com.laisd.moviesapp.data.datasource.local.LocalDataSource
import com.laisd.moviesapp.data.mapper.FavoriteMapper
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.FavoritesRepository
import io.reactivex.rxjava3.core.Single

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

    override fun getFavoriteMovieDetail(movieId: Int): Single<List<MovieDetail>> =
        localDataSource.getFavoriteMovies().map(favoriteMapper::mapFromEntityToMovieDetailList)

    override fun searchMovie(movieId: Int): Single<MovieDetail> =
        localDataSource.searchMovie(movieId).map(favoriteMapper::mapFromEntityToMovieDetail)

}