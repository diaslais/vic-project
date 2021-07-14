package com.laisd.moviesapp.domain.model

data class MovieDetail (
    val id: Int,
    val backdropPoster: String?,
    val title: String,
    val userRating: Float,
    val releaseDate: String,
    val filmCertification: String,
    val runtime: Int?,
    val genres: List<String>,
    val synopsis: String?,
    val cast: List<CastMember>
)