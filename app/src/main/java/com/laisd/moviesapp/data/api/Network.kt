package com.laisd.moviesapp.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/3/"

object Network {

    private val gson = GsonBuilder().create()
    private val okHttp = OkHttpClient.Builder().build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    fun createMoviesApiService(): MoviesApiService = retrofit.create(MoviesApiService::class.java)
}