package com.laisd.moviesapp.domain.model

data class MovieDetail (
    val id: Int,
    val backdropPoster: String?,
    val title: String,
    val userRating: String,
    val releaseDate: String,
    val filmCertification: String,
    val runtime: String?,
    val genres: List<String>,
    val synopsis: String?,
    val cast: List<CastMember>
)