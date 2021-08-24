package com.laisd.moviesapp.data.mapper

import com.laisd.moviesapp.data.model.remote.*
import com.laisd.moviesapp.domain.model.CastMember
import com.laisd.moviesapp.domain.model.Genre
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * The MovieMapper class is responsible for mapping server model objects to the app model,
 * as the app model contains only the data needed by the app.
 * These tests check if the methods of this class are doing the data conversion correctly.
 **/

class MovieMapperTest {

    val movieListResponse = MoviesListResponse(
        moviesList = arrayListOf(
            MovieResponse(
                id = 157336,
                poster = "/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
                title = "Interestelar",
                userRating = 8.3f
            ),
            MovieResponse(
                id = 64690,
                poster = "/1dREXakGbByZ1kti8vfVX3I8Dc.jpg",
                title = "Drive",
                userRating = 7.6f
            )
        )
    )

    val listOfMovie = arrayListOf(
        Movie(
            id = 157336,
            poster = "/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
            title = "Interestelar",
            userRating = "83%"
        ),
        Movie(
            id = 64690,
            poster = "/1dREXakGbByZ1kti8vfVX3I8Dc.jpg",
            title = "Drive",
            userRating = "76%"
        )
    )

    val movieDetailResponse = MovieDetailResponse(
        id = 64690,
        backdropPoster = "/9e5eRFCgQtrpyMdihe62O68yuZc.jpg",
        title = "Drive",
        userRating = 7.6f,
        releaseDate = "2011-06-17",
        releases = ReleasesListResponse(
            countries = arrayListOf<ReleaseResponse>(
                ReleaseResponse(
                    "US",
                    "R"
                ), ReleaseResponse("BR", "16")
            )
        ),
        runtime = 96,
        genres = arrayListOf(
            GenreResponse(18, "Drama"),
            GenreResponse(53, "Thriller"),
            GenreResponse(80, "Crime")
        ),
        synopsis = "Um motorista habilidoso é dublê em Hollywood e piloto de fuga em assaltos.",
        credits = CreditsResponse(
            arrayListOf(
                CastMemberResponse(
                    id = 30614,
                    name = "Ryan Gosling",
                    character = "Driver",
                    photo = "/lyUyVARQKhGxaxy0FbPJCQRpiaW.jpg"
                ),
                CastMemberResponse(
                    id = 36662,
                    name = "Carey Mulligan",
                    character = "Irene",
                    photo = "/6x31pMOFprcRhApmDnJ9yl0HeDT.jpg"
                )
            )
        )
    )

    val movieDetail = MovieDetail(
        id = 64690,
        backdropPoster = "/9e5eRFCgQtrpyMdihe62O68yuZc.jpg",
        title = "Drive",
        userRating = "76%",
        releaseDate = "2011",
        filmCertification = "16",
        runtime = "1h 36min",
        genres = arrayListOf("Drama", "Thriller", "Crime"),
        synopsis = "Um motorista habilidoso é dublê em Hollywood e piloto de fuga em assaltos.",
        cast = arrayListOf(
            CastMember(
                name = "Ryan Gosling",
                character = "Driver",
                photo = "/lyUyVARQKhGxaxy0FbPJCQRpiaW.jpg"
            ),
            CastMember(
                name = "Carey Mulligan",
                character = "Irene",
                photo = "/6x31pMOFprcRhApmDnJ9yl0HeDT.jpg"
            )
        )
    )

    val genreListResponse = GenreListResponse(
        genres = arrayListOf(
            GenreResponse(18, "Drama"),
            GenreResponse(53, "Thriller"),
            GenreResponse(80, "Crime")
        )
    )

    val genreList = arrayListOf(
        Genre(18, "Drama"),
        Genre(53, "Thriller"),
        Genre(80, "Crime")
    )

    val movieMapper = MovieMapper()

    /**
     * "toMovie" method transforms the MovieListResponse given by the API into a list of objects of
     * type Movie in the repository
     * **/
    @Test
    fun `return a list of Movie when a MovieListResponse is passed to method toMovie`() {
        assertEquals(listOfMovie, movieMapper.toMovie(movieListResponse))
    }

    /**
     * "toMovieDetail" method transforms object of type MovieDetailResponse given by the API into
     * object of type MovieDetail in the repository
     * **/
    @Test
    fun `return a MovieDetail when a MovieDetailResponse is passed to method toMovieDetail`() {
        assertEquals(movieDetail, movieMapper.toMovieDetail(movieDetailResponse))
    }

    /**
     * "toGenreList" method is used in the repository to transform an object of type
     * GenreListResponse given by the API into a list of objects of type Genre
     * **/
    @Test
    fun `return a list of Genre when a GenreListResponse is passed to method toGenreList`() {
        assertEquals(genreList, movieMapper.toGenreList(genreListResponse))
    }
}