package com.laisd.moviesapp.domain.model

data class Movie(
    val id: Int,
    val poster: String?,
    val title: String,
    val userRating: Float
)