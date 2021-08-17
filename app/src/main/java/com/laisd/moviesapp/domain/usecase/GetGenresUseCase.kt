package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig
import com.laisd.moviesapp.domain.model.Genre
import com.laisd.moviesapp.domain.repository.MovieRepository
import io.reactivex.rxjava3.core.Single

class GetGenresUseCase (private val movieRepository: MovieRepository) {

    fun execute(): Single<List<Genre>> = movieRepository.getGenres(BuildConfig.apiKey, "pt-BR")
}