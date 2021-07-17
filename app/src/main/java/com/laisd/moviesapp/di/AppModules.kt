package com.laisd.moviesapp.di

import com.laisd.moviesapp.data.repository.MoviesRepositoryImpl
import com.laisd.moviesapp.domain.repository.MoviesRepository
import com.laisd.moviesapp.domain.usecase.GetMovieDetailUseCase
import com.laisd.moviesapp.domain.usecase.GetMoviesUseCase
import com.laisd.moviesapp.presentation.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModules = module {
    viewModel { MoviesViewModel(moviesUseCase = get(), movieDetailUseCase = get()) }
}

val domainModules = module {
    factory { GetMoviesUseCase(moviesRepository = get()) }
    factory { GetMovieDetailUseCase(moviesRepository = get()) }
}

val dataModules = module {
    factory<MoviesRepository> { MoviesRepositoryImpl() }
}