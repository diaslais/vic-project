package com.laisd.moviesapp.data.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.laisd.moviesapp.data.api.*
import com.laisd.moviesapp.data.datasource.local.LocalDataSource
import com.laisd.moviesapp.data.datasource.local.LocalDataSourceImpl
import com.laisd.moviesapp.data.datasource.remote.RemoteDataSource
import com.laisd.moviesapp.data.datasource.remote.RemoteDataSourceImpl
import com.laisd.moviesapp.data.db.MovieDataBase
import com.laisd.moviesapp.data.mapper.FavoriteMapper
import com.laisd.moviesapp.data.mapper.MovieMapper
import com.laisd.moviesapp.data.repository.FavoritesRepositoryImpl
import com.laisd.moviesapp.data.repository.MovieRepositoryImpl
import com.laisd.moviesapp.domain.repository.FavoritesRepository
import com.laisd.moviesapp.domain.repository.MovieRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModules = module {
    factory<MovieRepository> { MovieRepositoryImpl(movieMapper = get(), remoteDataSource = get()) }
    factory<FavoritesRepository> { FavoritesRepositoryImpl(localDataSource = get(), favoriteMapper = get()) }
    factory { FavoriteMapper() }
    factory { MovieMapper() }
    factory<LocalDataSource> { LocalDataSourceImpl(movieDao = get()) }
    factory<RemoteDataSource> {
        RemoteDataSourceImpl(
            movieDetailService = get(),
            moviesService = get(),
            genresService = get(),
            moviesByGenreService = get(),
            searchMovieService = get()
        )
    }
}

val dataBaseModules = module {
    single {
        Room.databaseBuilder(androidApplication(), MovieDataBase::class.java, "moviesdatabase")
            .build()
    }
    factory { get<MovieDataBase>().movieDao() }
}

val networkModules = module {
    factory { get<Retrofit>().create(MoviesService::class.java) }
    factory { get<Retrofit>().create(MovieDetailService::class.java) }
    factory { get<Retrofit>().create(GenresService::class.java) }
    factory { get<Retrofit>().create(MoviesByGenreService::class.java) }
    factory { get<Retrofit>().create(SearchMovieService::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    single { GsonBuilder().create() }
    single { OkHttpClient.Builder().build() }
}