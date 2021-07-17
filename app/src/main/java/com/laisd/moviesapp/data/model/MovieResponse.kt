package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
        val id: Int,
        @SerializedName("poster_path")
        val poster: String?,
        val title: String,
        @SerializedName("vote_average")
        val userRating: Float,
)
