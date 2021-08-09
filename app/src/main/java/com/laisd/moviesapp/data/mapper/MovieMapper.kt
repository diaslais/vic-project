package com.laisd.moviesapp.data

import com.laisd.moviesapp.data.model.CastMemberResponse
import com.laisd.moviesapp.data.model.MovieDetailResponse
import com.laisd.moviesapp.data.model.MoviesListResponse
import com.laisd.moviesapp.domain.model.CastMember
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail

class MovieMapper {

    fun toMovies(moviesListResponse: MoviesListResponse): List<Movie> {
        return moviesListResponse.popularMovies.map {
            Movie(
                it.id,
                it.poster,
                it.title,
                it.userRating
            )
        }
    }

    fun toMovieDetail(movieDetailResponse: MovieDetailResponse): MovieDetail {
        val releasesResponseList = movieDetailResponse.releases.countries
        var filmCertification = ""
        releasesResponseList.forEach {
            if (it.countryCode == "BR") {
                filmCertification = it.certification
            }
        }

        val genres = arrayListOf<String>()
        movieDetailResponse.genres.forEach {
            genres.add(it.name)
        }

        val castMemberResponseList = movieDetailResponse.credits.cast
        val cast = castMemberResponseList.map { castMemberResponse ->
            toCastMember(castMemberResponse)
        }

        return MovieDetail(
            movieDetailResponse.id,
            movieDetailResponse.backdropPoster,
            movieDetailResponse.title,
            movieDetailResponse.userRating,
            movieDetailResponse.releaseDate,
            filmCertification,
            movieDetailResponse.runtime,
            genres,
            movieDetailResponse.synopsis,
            cast
        )
    }

    fun toCastMember(castMemberResponse: CastMemberResponse): CastMember {
        return CastMember(
            castMemberResponse.name,
            castMemberResponse.character,
            castMemberResponse.photo
        )
    }
}