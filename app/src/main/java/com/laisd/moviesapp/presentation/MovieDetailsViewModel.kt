package com.laisd.moviesapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.usecase.GetMovieDetailUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailsViewModel(
    private val movieDetailUseCase: GetMovieDetailUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail>
        get() = _movieDetail

    fun getMovieDetails(movieId: Int): LiveData<MovieDetail> {
        movieDetailUseCase.movieId = movieId
        movieDetailUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let { _movieDetail.value = it }
            }, Throwable::printStackTrace).let {
                compositeDisposable.add(it)
            }

        return movieDetail
    }

    private val _genresList = MutableLiveData<List<String>>(
        arrayListOf(
            "Ação",
            "Anime",
            "Comédia",
            "Drama",
            "Terror",
            "Antigos",
            "Suspense"
        )
    )
    val genresList: LiveData<List<String>>
        get() = _genresList

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}