package com.laisd.moviesapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.laisd.moviesapp.domain.model.Genre
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import com.laisd.moviesapp.domain.usecase.*
import com.laisd.moviesapp.domain.usecase.interfaces.MovieByIdUseCase
import com.laisd.moviesapp.domain.usecase.interfaces.MovieUseCase
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

    private var _movieDetail = MutableLiveData<MovieDetail?>()
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

    private val _movieDetailError = MutableLiveData<Boolean>()
    val movieDetailError: LiveData<Boolean> = _movieDetailError

    private val _movieNotFound = MutableLiveData<Boolean>()
    val movieNotFound: LiveData<Boolean> = _movieNotFound

    fun initializeLists() {
        getMovieList(getMoviesUseCase, _popularMovies)
        getMovieList(getFavoritesUseCase, _favoriteMovies)
        getGenres()
        _movieDetailError.value = false
    }

    private fun getMovieList(
        useCase: MovieUseCase<List<Movie>>,
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
        useCase: MovieByIdUseCase<MovieDetail>,
        subscribeFunctionCallback: (movieDetail: MovieDetail) -> Unit
    ) {
        useCase.execute(movieId)
            .subscribeOn(Schedulers.io())
            .subscribe(subscribeFunctionCallback, { _movieDetailError.postValue(true) })
            .let { compositeDisposable.add(it) }
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

    fun searchMovieFromApi(query: String) {
        searchMovieUseCase.execute(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ filteredMovies ->
                _searchFromApi.value = filteredMovies
                _movieNotFound.value = _searchFromApi.value.isNullOrEmpty()
            }, Throwable::printStackTrace).let { compositeDisposable.add(it) }
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