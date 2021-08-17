package com.laisd.moviesapp.data.model.remote

import com.google.gson.annotations.SerializedName
import com.laisd.moviesapp.data.model.remote.MovieResponse

data class MoviesListResponse(
    @SerializedName("results")
    var moviesList: List<MovieResponse>
)
