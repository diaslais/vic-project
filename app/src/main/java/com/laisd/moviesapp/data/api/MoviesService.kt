package com.laisd.moviesapp.data.api

import com.laisd.moviesapp.data.model.remote.MoviesListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("movie/popular")
    fun getPopularMoviesResponse(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MoviesListResponse>

}