package com.laisd.moviesapp.data.api

import com.laisd.moviesapp.data.model.MoviesListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/popular")
    fun getPopularMoviesResponse(
        @Query("api_key") api_key: String
    ): Single<MoviesListResponse>

}