package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.data.repository.MoviesRepository
import com.laisd.moviesapp.domain.model.MovieDetail
import io.reactivex.rxjava3.core.Single

class GetMovieDetailUseCase (private val movieId: Int, private val repository: MoviesRepository = MoviesRepository()){

    fun execute(): Single<MovieDetail> {
        return repository.getMovieDetail(movieId, apiKey, "pt-BR", "releases,credits")
    }
}