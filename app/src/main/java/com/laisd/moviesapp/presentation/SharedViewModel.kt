package com.laisd.moviesapp.presentation

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.laisd.moviesapp.R
import com.laisd.moviesapp.domain.model.Genre
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
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val getGenresUseCase: GetGenresUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    init {
        getPopularMovies()
        getFavoriteMovies()
        getGenres()
    }

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _favoriteMovies = MutableLiveData<List<Movie>>()
    val favoriteMovies: LiveData<List<Movie>> = _favoriteMovies

    private val _allGenres = MutableLiveData<List<Genre>>()
    val allGenres: LiveData<List<Genre>> = _allGenres

    private val _movieDetail = MutableLiveData<MovieDetail?>()
    val movieDetail: LiveData<MovieDetail?> = _movieDetail

    private val _genreTitles = MutableLiveData<List<String>>()
    val genreTitles: LiveData<List<String>> = _genreTitles

    private val _moviesByGenreFromApi = MutableLiveData<List<Movie>>()
    val moviesByGenreFromApi: LiveData<List<Movie>> = _moviesByGenreFromApi

    private val _moviesByGenreFromFavorites = MutableLiveData<List<Movie>>()
    val moviesByGenreFromFavorites: LiveData<List<Movie>> = _moviesByGenreFromFavorites

    private val _moviesByGenreInSearchMode = MutableLiveData<List<Movie>>()
    val moviesByGenreInSearchMode: LiveData<List<Movie>> = _moviesByGenreInSearchMode

    private val _searchFromApi = MutableLiveData<List<Movie>>()
    val searchFromApi: LiveData<List<Movie>> = _searchFromApi


    fun getPopularMovies() {
        getMoviesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it?.let { _popularMovies.value = it }
            }, Throwable::printStackTrace).let {
                compositeDisposable.add(it)
            }
    }

    fun getFavoriteMovies() {
        getFavoritesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ favoriteMovies ->
                _favoriteMovies.postValue(favoriteMovies)
            }, Throwable::printStackTrace).let {
                compositeDisposable.add(it)
            }
    }

    private fun getGenres() {
        getGenresUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listOfGenres ->
                _allGenres.postValue(listOfGenres)
            }, Throwable::printStackTrace).let { compositeDisposable.add(it) }
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
            .observeOn(Schedulers.io())
            .subscribe(subscribeFunctionCallback, { _movieDetail.postValue(null) })
            .let { compositeDisposable.add(it) }
    }

    private fun getMovieDetailFromDataBase(
        movieId: Int,
        subscribeFunctionCallback: (movieDetail: MovieDetail) -> Unit
    ) {
        getFavoriteDetailUseCase.execute(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(subscribeFunctionCallback, Throwable::printStackTrace)
            .let { compositeDisposable.add(it) }
    }

    fun setBackdropPoster(fragment: Fragment, movieDetail: MovieDetail, imageView: ImageView) {
        val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"
        var pictureUrl: String? = null
        movieDetail.backdropPoster?.let { pictureUrl = imageBaseUrl + it }
        Glide.with(fragment)
            .load(pictureUrl)
            .fallback(R.drawable.ic_baseline_android_24)
            .centerCrop()
            .into(imageView)
    }

    fun setHeartIcon(imageButton: ImageButton, movieId: Int) {
        if (movieIsFavorite(movieId)) {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            imageButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    fun movieIsFavorite(movieId: Int): Boolean {
        var isFavorite = false
        _favoriteMovies.value?.forEach { favoriteMovie ->
            if (movieId == favoriteMovie.id) isFavorite = true
        }
        return isFavorite
    }

    fun movieFoundBySearchMode(searchMode: Boolean) {
        if (searchMode) movieFromSearchMode = true
    }

    fun movieFoundByGenreFilter(genre: String?) {
        genre?.let { movieFromGenreFilter = genre }
    }

    private var movieFromSearchMode = false
    private var movieFromGenreFilter: String? = null

    fun favoriteClicked(movieId: Int) {
        if (movieIsFavorite(movieId)) {
            val movie = _favoriteMovies.value?.find { it.id == movieId }
            movie?.let { deleteFavorite(it) }
        } else {
            if (movieFromSearchMode) {
                val movie = _searchFromApi.value?.find { it.id == movieId }
                movie?.let { addFavorite(it) }
            } else if (movieFromGenreFilter != null) {
                val movie = _moviesByGenreFromApi.value?.find { it.id == movieId }
                movie?.let { addFavorite(it) }
            } else {
                val movie = _popularMovies.value?.find { it.id == movieId }
                movie?.let { addFavorite(it) }
            }
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

    fun genreTitles() {
        val titles = arrayListOf<String>()
        _allGenres.value?.forEach { genre ->
            titles.add(genre.title)
        }
        _genreTitles.value = titles
    }

    fun searchMovieFromApi(query: String, imageView: ImageView, viewPager2: ViewPager2) {
        searchMovieUseCase.execute(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ filteredMovies ->
                _searchFromApi.value = filteredMovies
                if (_searchFromApi.value.isNullOrEmpty()) {
                    movieNotFound(imageView, viewPager2)
                } else {
                    movieFound(imageView, viewPager2)
                }
            }, Throwable::printStackTrace).let { compositeDisposable.add(it) }
    }

    private fun movieFound(imageView: ImageView, viewPager2: ViewPager2) {
        viewPager2.visibility = View.VISIBLE
        imageView.visibility = View.INVISIBLE
    }

    private fun movieNotFound(imageView: ImageView, viewPager2: ViewPager2) {
        viewPager2.visibility = View.INVISIBLE
        imageView.visibility = View.VISIBLE
    }

    fun filterByGenreFromApi(genre: String) {
        val filteredList = arrayListOf<Movie>()
        getMoviesByGenreUseCase.execute(getGenreId(genre))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ moviesByGenre ->
                _moviesByGenreFromApi.value = moviesByGenre
            }, {
                _moviesByGenreFromApi.value = filteredList
            }).let { compositeDisposable.add(it) }
    }

    private fun getGenreId(genreTitle: String): Int {
        val genre = _allGenres.value?.find { it.title == genreTitle }
        return genre!!.id
    }

    fun filterByGenreFromFavorites(genre: String) {
        val filteredList = arrayListOf<Movie>()
        _moviesByGenreFromFavorites.postValue(filteredList)
        _favoriteMovies.value?.forEach { movie ->
            getMovieDetailFromDataBase(movie.id) { movieDetail ->
                movieDetail.genres.forEach { movieDetailGenre ->
                    if (movieDetailGenre == genre) {
                        filteredList.add(movie)
                        _moviesByGenreFromFavorites.postValue(filteredList)
                    }
                }
            }
        }
    }

    fun filterByGenreInSearchMode(genre: String) {
        val searchFilteredByGenre = arrayListOf<Movie>()
        _moviesByGenreInSearchMode.postValue(searchFilteredByGenre)
        _searchFromApi.value?.forEach { movieFromSearch ->
            getMovieDetailFromApi(movieFromSearch.id) { movieDetailFromSearch ->
                movieDetailFromSearch.genres.forEach { movieDetailGenre ->
                    if (movieDetailGenre == genre) {
                        searchFilteredByGenre.add(movieFromSearch)
                        _moviesByGenreInSearchMode.postValue(searchFilteredByGenre)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}