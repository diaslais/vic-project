package com.laisd.moviesapp.domain.usecase

import com.laisd.moviesapp.BuildConfig.apiKey
import com.laisd.moviesapp.data.model.PopularMoviesResponse
import com.laisd.moviesapp.data.repository.MoviesRepositoryImpl
import retrofit2.Call

class GetMoviesUseCase() :
    SingleUseCase<PopularMoviesResponse> {
    private val moviesRepository = MoviesRepositoryImpl()

    override fun execute(): Call<PopularMoviesResponse> {
        return moviesRepository.getPopularMoviesResponse(apiKey)
    }
}