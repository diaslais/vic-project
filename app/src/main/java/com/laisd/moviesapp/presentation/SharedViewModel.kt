package com.laisd.moviesapp.presentation

import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.usecase.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SharedViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getFavoriteDetailUseCase: GetFavoriteDetailUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    init {
        getPopularMovies()
        getFavoriteMovies()
    }

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail>
        get() = _movieDetail

    private val _favoriteMovies = MutableLiveData<List<Movie>>()
    val favoriteMovies: LiveData<List<Movie>>
        get() = _favoriteMovies

    private fun getPopularMovies() {
        getMoviesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let { _popularMovies.value = it }
            }, Throwable::printStackTrace).let {
                compositeDisposable.add(it)
            }
    }

    fun setMovieDetail(movieId: Int) {

        if (movieIsFavorite(movieId)) {
            getMovieDetailFromDataBase(movieId) { _movieDetail.postValue(it) }
        } else {
            getMovieDetailFromApi(movieId) { _movieDetail.postValue(it) }
        }
    }

    private fun getMovieDetailFromApi(
        movieId: Int,
        subscribeFunctionCallback: (movieDetail: MovieDetail) -> Unit
    ) {
        getMovieDetailUseCase.execute(movieId)
            .subscribeOn(Schedulers.io())
            .subscribe(subscribeFunctionCallback, Throwable::printStackTrace).let { compositeDisposable.add(it) }
    }

    private fun getMovieDetailFromDataBase(
        movieId: Int,
        subscribeFunctionCallback: (movieDetail: MovieDetail) -> Unit
    ){
        getFavoriteDetailUseCase.execute(movieId)
            .subscribeOn(Schedulers.io())
            .subscribe(subscribeFunctionCallback, Throwable::printStackTrace).let { compositeDisposable.add(it) }
    }

    fun setBackdropPoster(fragment: Fragment, movieDetail: MovieDetail, imageView: ImageView) {
        val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"
        var pictureUrl: String? = null
        movieDetail.backdropPoster?.let { pictureUrl = imageBaseUrl + it }
        Glide.with(fragment)
            .load(pictureUrl)
            .fallback(R.drawable.drive)
            .centerCrop()
            .into(imageView)
    }

    fun setHeartIcon(imageButton: ImageButton, movieDetail: MovieDetail) {
        if (movieIsFavorite(movieDetail.id)) {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    fun movieIsFavorite(movieId: Int): Boolean {
        var isFavorite = false
        if (_favoriteMovies.value != null) {
            _favoriteMovies.value!!.forEach { favoriteMovie ->
                if (movieId == favoriteMovie.id) isFavorite = true
            }
        }
        return isFavorite
    }

    fun favoriteClicked(movie: Movie) {
        if (movieIsFavorite(movie.id)) {
            deleteFavorite(movie)
        } else {
            addFavorite(movie)
        }
    }

    private fun addFavorite(movie: Movie) {
        getMovieDetailFromApi(movie.id) { movieDetail ->
            addFavoriteUseCase.execute(movie, movieDetail)
            getFavoriteMovies()
        }
    }

    private fun deleteFavorite(movie: Movie) {
        getMovieDetailFromDataBase(movie.id) { movieDetail ->
            deleteFavoriteUseCase.execute(movie, movieDetail)
            getFavoriteMovies()
        }
    }

    private fun getFavoriteMovies() {
        getFavoritesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .subscribe({ favoriteMovies ->
                _favoriteMovies.postValue(favoriteMovies)
            }, Throwable::printStackTrace).let {
                compositeDisposable.add(it)
            }
    }

    //region genres
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

    //endregion genres

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}