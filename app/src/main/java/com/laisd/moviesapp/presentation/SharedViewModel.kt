package com.laisd.moviesapp.presentation

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
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
import com.laisd.moviesapp.domain.usecase.base.SingleByIdUseCase
import com.laisd.moviesapp.domain.usecase.base.SingleUseCase
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

    fun initializeLists() {
        getMovieList(getMoviesUseCase, _popularMovies)
        getMovieList(getFavoritesUseCase, _favoriteMovies)
        getGenres()
    }

    private fun getMovieList(
        useCase: SingleUseCase<List<Movie>>,
        movieList: MutableLiveData<List<Movie>>
    ) {
        useCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it?.let { movieList.value = it } }, Throwable::printStackTrace)
            .let { compositeDisposable.add(it) }
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
            getMovieDetail(movieId, getFavoriteDetailUseCase) { _movieDetail.postValue(it) }
        } else {
            getMovieDetail(movieId, getMovieDetailUseCase) { _movieDetail.postValue(it) }
        }
    }

    private fun getMovieDetail(
        movieId: Int,
        useCase: SingleByIdUseCase<MovieDetail>,
        subscribeFunctionCallback: (movieDetail: MovieDetail) -> Unit) {
            useCase.execute(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(subscribeFunctionCallback, { _movieDetail.postValue(null) })
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
        getMovieDetail(movie.id, getMovieDetailUseCase) { movieDetail ->
            addFavoriteUseCase.execute(movie, movieDetail)
            getMovieList(getFavoritesUseCase, _favoriteMovies)
        }
    }

    private fun deleteFavorite(movie: Movie) {
        getMovieDetail(movie.id, getFavoriteDetailUseCase) { movieDetail ->
            deleteFavoriteUseCase.execute(movie, movieDetail)
            getMovieList(getFavoritesUseCase, _favoriteMovies)
        }
    }

    fun genreTitles() {
        val titles = arrayListOf<String>()
        _allGenres.value?.forEach { genre ->
            titles.add(genre.title)
        }
        _genreTitles.value = titles
    }

    fun searchMovieFromApi(query: String, imageView: ImageView, viewPager2: ViewPager2, tv: TextView, tvDescription: TextView) {
        searchMovieUseCase.execute(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ filteredMovies ->
                _searchFromApi.value = filteredMovies
                if (_searchFromApi.value.isNullOrEmpty()) {
                    movieNotFound(imageView, viewPager2, tv, tvDescription)
                } else {
                    movieFound(imageView, viewPager2, tv, tvDescription)
                }
            }, Throwable::printStackTrace).let { compositeDisposable.add(it) }
    }

    private fun movieFound(imageView: ImageView, viewPager2: ViewPager2, tv: TextView, tvDescription: TextView) {
        viewPager2.visibility = View.VISIBLE
        imageView.visibility = View.INVISIBLE
        tv.visibility = View.INVISIBLE
        tvDescription.visibility = View.INVISIBLE
    }

    private fun movieNotFound(imageView: ImageView, viewPager2: ViewPager2, tv: TextView, tvDescription: TextView) {
        viewPager2.visibility = View.INVISIBLE
        imageView.visibility = View.VISIBLE
        tv.visibility = View.VISIBLE
        tvDescription.visibility = View.VISIBLE
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
            getMovieDetail(movie.id, getFavoriteDetailUseCase) { movieDetail ->
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
            getMovieDetail(movieFromSearch.id, getMovieDetailUseCase) { movieDetailFromSearch ->
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