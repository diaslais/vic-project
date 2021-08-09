package com.laisd.moviesapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.laisd.moviesapp.data.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}