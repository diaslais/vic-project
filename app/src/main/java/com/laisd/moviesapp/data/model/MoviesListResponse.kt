package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MoviesListResponse(
    @SerializedName("results")
    var popularMovies: List<MovieResponse>
)