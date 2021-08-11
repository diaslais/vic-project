package com.laisd.moviesapp.data.repository

import com.laisd.moviesapp.data.datasource.remote.RemoteDataSource
import com.laisd.moviesapp.data.mapper.MovieMapper
import com.laisd.moviesapp.domain.model.Genre
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class MovieRepositoryImpl(
    private val movieMapper: MovieMapper,
    private val remoteDataSource: RemoteDataSource
) : MovieRepository {

    override fun getMovies(apiKey: String, language: String): Single<List<Movie>> =
        remoteDataSource.getMovies(apiKey, language).map(movieMapper::toMovie)

    override fun getMovieDetail(movieId: Int, apiKey: String, language: String, appendToResponse: String): Single<MovieDetail> =
        remoteDataSource.getMovieDetail(movieId, apiKey, language, appendToResponse).map(movieMapper::toMovieDetail)

    override fun getGenres(apiKey: String, language: String): Single<List<Genre>> =
        remoteDataSource.getGenres(apiKey, language).map(movieMapper::toGenreList)

    override fun getMoviesByGenre(apiKey: String, language: String, genres: String): Single<List<Movie>> =
        remoteDataSource.getMoviesByGenre(apiKey, language, genres).map(movieMapper::toMovie)

    override fun searchMovie(apiKey: String, language: String, query: String): Single<List<Movie>> =
        remoteDataSource.searchMovie(apiKey, language, query).map(movieMapper::toMovie)

}