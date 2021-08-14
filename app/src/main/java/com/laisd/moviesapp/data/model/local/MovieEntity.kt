package com.laisd.moviesapp.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.laisd.moviesapp.domain.model.CastMember

@Entity (tableName = "movie")
data class MovieEntity(
    @PrimaryKey @ColumnInfo val movieId: Int,
    @ColumnInfo val poster: String?,
    @ColumnInfo val title: String,
    @ColumnInfo val userRating: String,
    @ColumnInfo val backdropPoster: String?,
    @ColumnInfo val releaseDate: String,
    @ColumnInfo val filmCertification: String,
    @ColumnInfo val runtime: String?,
    @ColumnInfo val genres: List<String>,
    @ColumnInfo val synopsis: String?,
    @ColumnInfo val cast: List<CastMember>
)

