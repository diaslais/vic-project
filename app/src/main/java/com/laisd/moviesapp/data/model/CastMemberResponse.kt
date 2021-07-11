package com.laisd.moviesapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CastMemberResponse(
    val name: String,
    val role: String,
    val picture: Int
) : Parcelable