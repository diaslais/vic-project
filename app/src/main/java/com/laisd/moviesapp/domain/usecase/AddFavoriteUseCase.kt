package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.FavoritesRepository

class AddFavoriteUseCase(private val favoritesRepository: FavoritesRepository) {

    fun execute(movie: Movie, movieDetail: MovieDetail) {
        favoritesRepository.addFavoriteMovie(movie, movieDetail)
    }
}