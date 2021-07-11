package com.laisd.moviesapp.data.api

import com.laisd.moviesapp.data.model.PopularMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/popular")
    fun getPopularMoviesList(
        @Query("api_key") api_key: String
    ): Call<PopularMoviesResponse>

}