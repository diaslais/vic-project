package com.laisd.moviesapp.data.mapper

import com.laisd.moviesapp.data.model.local.MovieEntity
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail

class FavoriteMapper {

    fun mapFromEntityToMovie(favoriteMovies: List<MovieEntity>): List<Movie> =
        favoriteMovies.map { movieEntity ->
            Movie(
                movieEntity.movieId,
                movieEntity.poster,
                movieEntity.title,
                movieEntity.userRating
            )
        }

    fun mapFromEntityToMovieDetail(movieEntity: MovieEntity): MovieDetail =
        MovieDetail(
            movieEntity.movieId,
            movieEntity.backdropPoster,
            movieEntity.title,
            movieEntity.userRating,
            movieEntity.releaseDate,
            movieEntity.filmCertification,
            movieEntity.runtime,
            movieEntity.genres,
            movieEntity.synopsis,
            movieEntity.cast
        )

    fun mapToEntity(movie: Movie, movieDetail: MovieDetail): MovieEntity =
        MovieEntity(
            movie.id,
            movie.poster,
            movie.title,
            movie.userRating,
            movieDetail.backdropPoster,
            movieDetail.releaseDate,
            movieDetail.filmCertification,
            movieDetail.runtime,
            movieDetail.genres,
            movieDetail.synopsis,
            movieDetail.cast
        )
}