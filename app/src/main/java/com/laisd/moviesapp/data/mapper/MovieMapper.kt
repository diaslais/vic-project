package com.laisd.moviesapp.data.mapper

import com.laisd.moviesapp.data.model.CastMemberResponse
import com.laisd.moviesapp.data.model.MovieDetailResponse
import com.laisd.moviesapp.data.model.MoviesListResponse
import com.laisd.moviesapp.domain.model.CastMember
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail

class MovieMapper {

    fun toMovie(moviesListResponse: MoviesListResponse): List<Movie> =
        moviesListResponse.popularMovies.map { movieResponse ->
            Movie(
                movieResponse.id,
                movieResponse.poster,
                movieResponse.title,
                movieResponse.userRating
            )
        }

    fun toMovieDetail(movieDetailResponse: MovieDetailResponse): MovieDetail =
        MovieDetail(
            movieDetailResponse.id,
            movieDetailResponse.backdropPoster,
            movieDetailResponse.title,
            movieDetailResponse.userRating,
            movieDetailResponse.releaseDate,
            getFilmCertification(movieDetailResponse),
            movieDetailResponse.runtime,
            getGenresList(movieDetailResponse),
            movieDetailResponse.synopsis,
            getCast(movieDetailResponse)
        )

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
}