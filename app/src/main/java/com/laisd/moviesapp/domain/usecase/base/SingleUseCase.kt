package com.laisd.moviesapp.domain.usecase.base

import io.reactivex.rxjava3.core.Single

interface SingleUseCase<R> {
    fun execute(): Single<R>
}