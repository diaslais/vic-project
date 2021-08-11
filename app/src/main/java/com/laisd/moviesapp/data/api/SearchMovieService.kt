package com.laisd.moviesapp.data.api

import com.laisd.moviesapp.data.model.MoviesListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchMovieService {

    @GET("search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Single<MoviesListResponse>

}