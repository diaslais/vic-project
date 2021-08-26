package com.laisd.moviesapp.domain.usecase.interfaces

import io.reactivex.rxjava3.core.Single

interface MovieUseCase<R> {
    fun execute(): Single<R>
}