package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
        @SerializedName("id")
        val id: Int,
        @SerializedName("poster_path")
        val poster: String?,
        @SerializedName("title")
        val title: String,
        @SerializedName("vote_average")
        val userRating: Float,
)
