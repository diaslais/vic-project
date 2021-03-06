package com.laisd.moviesapp.data.db

import androidx.room.*
import com.laisd.moviesapp.data.model.local.MovieEntity
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavoriteMovie(movieEntity: MovieEntity)

    @Delete
    fun deleteFavoriteMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movie")
    fun getFavoriteMovies(): Single<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE movieId = :movieId LIMIT 1")
    fun getFavoriteMovieDetail(movieId: Int): Single<MovieEntity>
}