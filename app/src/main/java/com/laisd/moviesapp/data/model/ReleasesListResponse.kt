package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class ReleasesListResponse (
    @SerializedName("countries")
    val countries: List<ReleaseResponse>
)

data class ReleaseResponse (
    @SerializedName("iso_3166_1")
    val countryCode: String,
    @SerializedName("certification")
    val certification: String
)