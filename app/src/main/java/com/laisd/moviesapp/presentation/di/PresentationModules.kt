package com.laisd.moviesapp.presentation.di

import com.laisd.moviesapp.presentation.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModules = module {
    viewModel {
        SharedViewModel(
            getMoviesUseCase = get(),
            getMovieDetailUseCase = get(),
            getFavoritesUseCase = get(),
            getFavoriteDetailUseCase = get(),
            addFavoriteUseCase = get(),
            deleteFavoriteUseCase = get(),
            getGenresUseCase = get(),
            getMoviesByGenreUseCase = get(),
            searchMovieUseCase = get()
        )
    }
}