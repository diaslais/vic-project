package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.repository.FavoritesRepository
import com.laisd.moviesapp.domain.usecase.base.SingleByIdUseCase
import io.reactivex.rxjava3.core.Single

class GetFavoriteDetailUseCase(private val favoritesRepository: FavoritesRepository): SingleByIdUseCase<MovieDetail> {

    override fun execute(movieId: Int): Single<MovieDetail> =
        favoritesRepository.getFavoriteMovieDetail(movieId)
}