package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class GenreListResponse(
    @SerializedName("genres")
    val genres: List<GenreResponse>
)
