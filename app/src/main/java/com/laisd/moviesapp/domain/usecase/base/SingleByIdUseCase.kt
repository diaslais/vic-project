package com.laisd.moviesapp.domain.usecase.base

import io.reactivex.rxjava3.core.Single

interface SingleByIdUseCase<R> {
    fun execute(movieId: Int): Single<R>
}