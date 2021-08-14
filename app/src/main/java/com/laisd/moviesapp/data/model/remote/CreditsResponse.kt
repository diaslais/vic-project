package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class CreditsResponse (
    @SerializedName("cast")
    val cast: List<CastMemberResponse>
)

data class CastMemberResponse (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("character")
    val character: String,
    @SerializedName("profile_path")
    val photo: String?
)