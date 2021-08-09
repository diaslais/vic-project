package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val backdropPoster: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("vote_average")
    val userRating: Float,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("releases")
    val releases: ReleasesListResponse,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("genres")
    val genres: List<GenreResponse>,
    @SerializedName("overview")
    val synopsis: String?,
    @SerializedName("credits")
    val credits: CreditsResponse
)