package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.domain.model.MoviesList
import com.laisd.moviesapp.domain.repository.MoviesRepository
import io.reactivex.rxjava3.core.Single

class GetMoviesUseCase(private val moviesRepository: MoviesRepository) {

    fun execute(): Single<MoviesList> {
        return moviesRepository.getMovies(apiKey, "pt-BR")
    }
}
