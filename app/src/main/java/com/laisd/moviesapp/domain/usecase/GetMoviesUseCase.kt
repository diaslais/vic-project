package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.data.repository.MoviesRepository
import com.laisd.moviesapp.domain.model.MoviesList
import io.reactivex.rxjava3.core.Single

class GetMoviesUseCase(private val repository: MoviesRepository = MoviesRepository()) {

    fun execute(): Single<MoviesList> {
        return repository.getMovies(apiKey, "pt-BR")
    }
}