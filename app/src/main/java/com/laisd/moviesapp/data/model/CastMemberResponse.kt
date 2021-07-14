package com.laisd.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class CastMemberResponse (
    val id: Int,
    val name: String,
    val character: String,
    @SerializedName("profile_path")
    val photo: String?
)