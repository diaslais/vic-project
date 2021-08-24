package com.laisd.moviesapp.data.mapper

import com.laisd.moviesapp.data.model.local.MovieEntity
import com.laisd.moviesapp.domain.model.CastMember
import com.laisd.moviesapp.domain.model.Movie
import com.laisd.moviesapp.domain.model.MovieDetail
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * The FavoriteMapper class contains methods that map domain layer model objects to database
 * model objects and vice versa.
 * They are used to access database, add and remove items and get the list of favorite movies.
 **/

class FavoriteMapperTest {

    val movieEntityA = MovieEntity(
        movieId = 64690,
        poster = "/1dREXakGbByZ1kti8vfVX3I8Dc.jpg",
        title = "Drive",
        userRating = "76%",
        backdropPoster = "/9e5eRFCgQtrpyMdihe62O68yuZc.jpg",
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

    val movieEntityB = MovieEntity(
        movieId = 157336,
        poster = "/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        title = "Interestelar",
        userRating = "83%",
        backdropPoster = "/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        releaseDate = "2014",
        filmCertification = "10",
        runtime = "2h 49min",
        genres = arrayListOf("Aventura", "Drama", "Ficção científica"),
        synopsis = "As reservas naturais da Terra estão chegando ao fim e um grupo de astronautas recebe a missão de verificar possíveis planetas.",
        cast = arrayListOf(
            CastMember(
                name = "Matthew McConaughey",
                character = "Joseph",
                photo = "/wJiGedOCZhwMx9DezY8uwbNxmAY.jpg"
            ),
            CastMember(
                name = "Anne Hathaway",
                character = "Dr. Amelia Brand",
                photo = "/tLelKoPNiyJCSEtQTz1FGv4TLGc.jpg"
            ),
            CastMember(
                name = "Jessica Chastain",
                character = "Murphy Cooper",
                photo = "/lodMzLKSdrPcBry6TdoDsMN3Vge.jpg"
            )
        )
    )

    val movieA = Movie(
        id = 64690,
        poster = "/1dREXakGbByZ1kti8vfVX3I8Dc.jpg",
        title = "Drive",
        userRating = "76%"
    )

    val movieB = Movie(
        id = 157336,
        poster = "/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        title = "Interestelar",
        userRating = "83%"
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

    val favoriteMapper = FavoriteMapper()

    /**
     * "mapToEntity" method is used to transform an object of type Movie and one of type
     * MovieDetail into a MovieEntity, used to add or remove an item from the database
     * **/
    @Test
    fun `return a MovieEntity object when a Movie and MovieDetail is passed to mapToEntity method`() {
        assertEquals(movieEntityA, favoriteMapper.mapToEntity(movieA, movieDetail))
    }

    /**
     * "mapFromEntityToMovieDetail" method receives a MovieEntity object and maps it
     * into an object of type MovieDetail
     * **/
    @Test
    fun `return a MovieDetail when a MovieEntity is passed to mapFromEntityToMovieDetaill method`() {
        assertEquals(movieDetail, favoriteMapper.mapFromEntityToMovieDetail(movieEntityA))
    }

    /**
     * "mapFromEntityToMovie" method receives a list of MovieEntity objects from database and
     * maps it into a list of objects of type Movie
     * **/
    @Test
    fun `return a list of Movie when a list of MovieEntity is given to mapFromEntityToMovie method`() {
        assertEquals(
            arrayListOf(movieA, movieB),
            favoriteMapper.mapFromEntityToMovie(arrayListOf(movieEntityA, movieEntityB))
        )
    }
}