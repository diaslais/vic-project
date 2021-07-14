package com.laisd.moviesapp.data.api

import com.laisd.moviesapp.data.model.MovieDetailResponse
import com.laisd.moviesapp.data.model.MoviesListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/popular")
    fun getPopularMoviesResponse(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): Single<MoviesListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetailResponse(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("append_to_response") append_to_response: String
    ): Single<MovieDetailResponse>

}