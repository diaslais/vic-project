package com.laisd.moviesapp.domain.usecase.interfaces

import io.reactivex.rxjava3.core.Single

interface MovieByIdUseCase<R> {
    fun execute(movieId: Int): Single<R>
}