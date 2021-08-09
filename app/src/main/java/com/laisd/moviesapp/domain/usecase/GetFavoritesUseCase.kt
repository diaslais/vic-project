package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.repository.FavoritesRepository
import io.reactivex.rxjava3.core.Single

class GetFavoritesUseCase(private val favoritesRepository: FavoritesRepository){

    fun execute(): Single<List<Movie>> =
        favoritesRepository.getFavoriteMovies()
}