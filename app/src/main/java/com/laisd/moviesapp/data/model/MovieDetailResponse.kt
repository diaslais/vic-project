package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse (
    val id: Int,
    @SerializedName("backdrop_path")
    val backdropPoster: String?,
    val title: String,
    @SerializedName("vote_average")
    val userRating: Float,
    @SerializedName("release_date")
    val releaseDate: String,
    val releases: ReleasesListResponse,
    val runtime: Int?,
    val genres: List<GenreResponse>,
    @SerializedName("overview")
    val synopsis: String?,
    val credits: CreditsResponse
)