package com.laisd.moviesapp.data

import com.laisd.moviesapp.data.model.MovieResponse
import com.laisd.moviesapp.data.model.MoviesListResponse
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MoviesList

class MovieMapper {

    fun toPopularMovies(popularMoviesResponse: MoviesListResponse): MoviesList {
        return MoviesList(
            popularMoviesResponse.popularMovies.map { movieResponse ->
                toMovie(movieResponse)
            }
        )
    }

    private fun toMovie(movieResponse: MovieResponse): Movie {
        return Movie(
            movieResponse.title ?: ""
        )
    }
}