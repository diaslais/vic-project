package com.laisd.moviesapp.data.db

import androidx.room.*
import com.laisd.moviesapp.data.model.MovieEntity
import com.laisd.moviesapp.domain.model.MovieDetail
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
    fun searchMovie(movieId: Int): Single<MovieEntity>
}