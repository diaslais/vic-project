package com.laisd.moviesapp.data.mapper

import com.laisd.moviesapp.data.model.remote.CastMemberResponse
import com.laisd.moviesapp.data.model.remote.GenreListResponse
import com.laisd.moviesapp.data.model.remote.MovieDetailResponse
import com.laisd.moviesapp.data.model.remote.MoviesListResponse
import com.laisd.moviesapp.domain.model.CastMember
import com.laisd.moviesapp.domain.model.Genre
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail

class MovieMapper {

    fun toMovie(moviesListResponse: MoviesListResponse): List<Movie> =
        moviesListResponse.moviesList.map { movieResponse ->
            Movie(
                movieResponse.id,
                movieResponse.poster,
                movieResponse.title,
                formatUserRating(movieResponse.userRating)
            )
        }

    fun toMovieDetail(movieDetailResponse: MovieDetailResponse): MovieDetail =
        MovieDetail(
            movieDetailResponse.id,
            movieDetailResponse.backdropPoster,
            movieDetailResponse.title,
            formatUserRating(movieDetailResponse.userRating),
            formatReleaseDate(movieDetailResponse.releaseDate),
            getFilmCertification(movieDetailResponse),
            formatMovieRuntime(movieDetailResponse.runtime),
            getGenresList(movieDetailResponse),
            movieDetailResponse.synopsis,
            getCast(movieDetailResponse)
        )

    private fun formatUserRating(userRating: Float): String =
        (userRating * 10).toInt().toString() + "%"

    private fun formatReleaseDate(date: String): String = date.take(4)

    private fun formatMovieRuntime(runtime: Int?): String? {
        var movieRuntime: String? = null
        runtime?.let {
            val hours = it / 60
            val minutes = it % 60
            movieRuntime = hours.toString() + "h " + minutes.toString() + "min"
        }
        return movieRuntime
    }

    private fun getFilmCertification(movieDetailResponse: MovieDetailResponse): String {
        val releasesResponseList = movieDetailResponse.releases.countries
        var filmCertification = ""
        releasesResponseList.forEach {
            if (it.countryCode == "BR") {
                filmCertification = it.certification
            }
        }
        return filmCertification
    }

    private fun getGenresList(movieDetailResponse: MovieDetailResponse): List<String> {
        val genres = arrayListOf<String>()
        movieDetailResponse.genres.forEach {
            genres.add(it.name)
        }
        return genres
    }

    private fun getCast(movieDetailResponse: MovieDetailResponse): List<CastMember> {
        val castMemberResponseList = movieDetailResponse.credits.cast
        return castMemberResponseList.map { castMemberResponse ->
            toCastMember(castMemberResponse)
        }
    }

    private fun toCastMember(castMemberResponse: CastMemberResponse): CastMember =
        CastMember(
            castMemberResponse.name,
            castMemberResponse.character,
            castMemberResponse.photo
        )

    fun toGenreList(genreListResponse: GenreListResponse): List<Genre> =
        genreListResponse.genres.map { genreResponse ->
            Genre(genreResponse.id, genreResponse.name)
        }
}