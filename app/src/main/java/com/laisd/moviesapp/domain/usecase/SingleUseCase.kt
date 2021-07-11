package com.laisd.moviesapp.domain.usecase

import retrofit2.Call

interface SingleUseCase<R> {
    fun execute(): Call<R>
}