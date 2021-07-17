package com.laisd.moviesapp.di

import com.laisd.moviesapp.data.MovieMapper
import com.laisd.moviesapp.data.repository.MovieDetailRepositoryImpl
import com.laisd.moviesapp.data.repository.MovieRepositoryImpl
import com.laisd.moviesapp.domain.repository.MovieDetailRepository
import com.laisd.moviesapp.domain.repository.MovieRepository
import com.laisd.moviesapp.domain.usecase.GetMovieDetailUseCase
import com.laisd.moviesapp.domain.usecase.GetMoviesUseCase
import com.laisd.moviesapp.presentation.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModules = module {
    viewModel { MoviesViewModel(moviesUseCase = get(), movieDetailUseCase = get()) }
}

val domainModules = module {
    factory { GetMoviesUseCase(movieRepository = get()) }
    factory { GetMovieDetailUseCase(movieDetailRepository = get()) }
}

val dataModules = module {
    factory<MovieRepository> { MovieRepositoryImpl(movieMapper = get()) }
    factory<MovieDetailRepository> { MovieDetailRepositoryImpl(movieMapper = get()) }
    factory { MovieMapper() }
}