package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
        val id: Int, //GET /movie/popular
        @SerializedName("poster_path")
        val poster: String?, //poster_path String? GET /movie/popular
        val title: String, //title String GET /movie/popular
        @SerializedName("vote_average")
        val userRating: Float, //vote_average Number GET /movie/popular
)
