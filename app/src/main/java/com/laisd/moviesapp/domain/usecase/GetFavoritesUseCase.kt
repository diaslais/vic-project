package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.repository.FavoritesRepository
import com.laisd.moviesapp.domain.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single

class GetFavoritesUseCase(private val favoritesRepository: FavoritesRepository): SingleUseCase<List<Movie>>{

    override fun execute(): Single<List<Movie>> =
        favoritesRepository.getFavoriteMovies()
}