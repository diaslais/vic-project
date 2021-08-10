package com.laisd.moviesapp.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import com.laisd.moviesapp.data.api.MovieDetailService
import com.laisd.moviesapp.data.api.MovieGenresService
import com.laisd.moviesapp.data.api.MoviesService
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
import com.laisd.moviesapp.domain.usecase.*
import com.laisd.moviesapp.presentation.SharedViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val presentationModules = module {
    viewModel {
        SharedViewModel(
            getMoviesUseCase = get(),
            getMovieDetailUseCase = get(),
            getFavoritesUseCase = get(),
            getFavoriteDetailUseCase = get(),
            addFavoriteUseCase = get(),
            deleteFavoriteUseCase = get(),
            getGenresUseCase = get()
        )
    }
}

val domainModules = module {
    factory { GetMoviesUseCase(movieRepository = get()) }
    factory { GetMovieDetailUseCase(movieRepository = get()) }
    factory { GetFavoritesUseCase(favoritesRepository = get()) }
    factory { GetFavoriteDetailUseCase(favoritesRepository = get()) }
    factory { AddFavoriteUseCase(favoritesRepository = get()) }
    factory { DeleteFavoriteUseCase(favoritesRepository = get()) }
    factory { GetGenresUseCase(movieRepository = get()) }
}

val dataModules = module {
    factory<MovieRepository> { MovieRepositoryImpl(movieMapper = get(), remoteDataSource = get()) }
    factory<FavoritesRepository> { FavoritesRepositoryImpl(localDataSource = get(), favoriteMapper = get()) }
    factory { FavoriteMapper() }
    factory { MovieMapper() }
    factory<LocalDataSource> { LocalDataSourceImpl(movieDao = get()) }
    factory<RemoteDataSource> { RemoteDataSourceImpl(movieDetailService = get(), moviesService = get(), movieGenresService = get()) }
}

val dataBaseModules = module {
    single { Room.databaseBuilder(androidApplication(), MovieDataBase::class.java, "moviesdatabase").build() }
    single { get<MovieDataBase>().movieDao() }
}

val networkModules = module {
    factory { get<Retrofit>().create(MoviesService::class.java) }
    factory { get<Retrofit>().create(MovieDetailService::class.java) }
    factory { get<Retrofit>().create(MovieGenresService::class.java) }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/") //colocar no build config
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }
    single { GsonBuilder().create() }
    single { OkHttpClient.Builder().build() }
}