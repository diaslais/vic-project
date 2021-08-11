package com.laisd.moviesapp.data.api

import com.laisd.moviesapp.data.model.GenreListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresService {

    @GET("genre/movie/list")
    fun getGenresResponse(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<GenreListResponse>

}