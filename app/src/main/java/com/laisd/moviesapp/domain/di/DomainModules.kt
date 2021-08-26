package com.laisd.moviesapp.domain.di

import com.laisd.moviesapp.domain.usecase.*
import org.koin.dsl.module

val domainModules = module {
    factory { GetMoviesUseCase(movieRepository = get()) }
    factory { GetMovieDetailUseCase(movieRepository = get()) }
    factory { GetFavoritesUseCase(favoritesRepository = get()) }
    factory { GetFavoriteDetailUseCase(favoritesRepository = get()) }
    factory { AddFavoriteUseCase(favoritesRepository = get()) }
    factory { DeleteFavoriteUseCase(favoritesRepository = get()) }
    factory { GetGenresUseCase(movieRepository = get()) }
    factory { GetMoviesByGenreUseCase(movieRepository = get()) }
    factory { SearchMovieUseCase(movieRepository = get()) }
}