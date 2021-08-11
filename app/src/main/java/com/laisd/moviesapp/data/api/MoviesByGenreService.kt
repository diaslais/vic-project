package com.laisd.moviesapp.data.api

import com.laisd.moviesapp.data.model.MoviesListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesByGenreService {

    @GET("discover/movie")
    fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("with_genres") genres: String
    ): Single<MoviesListResponse>

}