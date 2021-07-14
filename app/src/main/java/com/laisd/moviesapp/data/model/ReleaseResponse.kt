package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class ReleaseResponse (
    @SerializedName("iso_3166_1")
    val countryCode: String,
    val certification: String
)