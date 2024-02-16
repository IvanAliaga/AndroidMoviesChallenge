package com.android.movieschallenge.domain

import com.android.movieschallenge.data.database.MovieRepository
import com.android.movieschallenge.domain.model.Movie
import com.android.movieschallenge.domain.usecase.MoviesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MoviesUseCaseTest {
    @RelaxedMockK
    private lateinit var movieRepository: MovieRepository

    private lateinit var movieUseCase: MoviesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        movieUseCase = MoviesUseCase(movieRepository)
    }

    @Test
    fun `when the api doesnt return anything then get values from database`() = runBlocking {
        //Given
        coEvery { movieRepository.getAllMoviesFromApi(1) } returns emptyList()

        //When
        movieUseCase.getMovies(1)

        //Then
        coVerify(exactly = 1) { movieRepository.getAllMoviesFromDatabase(1) }
    }

    @Test
    fun `when the api doesnt return anything then get movie from database`() = runBlocking {

        //Given
        val emptyMovie: Movie = Movie(-1, "", 0.0, "", "", "")
        coEvery { movieRepository.getMovieFromApi(123) } returns emptyMovie

        //When
        movieUseCase.getMovie(123)

        //Then
        coVerify(exactly = 1) { movieRepository.getMovieFromDatabase(123) }
    }



    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        //Given
        val myList = listOf(Movie(1, "Pelicula 1", 0.0, "", "", ""))
        coEvery { movieRepository.getAllMoviesFromApi(1) } returns myList

        //When
        val response = movieUseCase.getMovies(1)

        //Then
        //coVerify(exactly = 1) { movieRepository.clearMovies() }
        coVerify(exactly = 1) { movieRepository.insertMovies(any()) }
        coVerify(exactly = 0) { movieRepository.getAllMoviesFromDatabase(1) }
        assert(response == myList)
    }

    @Test
    fun `when the api return something then get movie from api`() = runBlocking {
        //Given
        val movie = Movie(1, "Pelicula 1", 0.0, "", "", "")
        coEvery { movieRepository.getMovieFromApi(1) } returns movie

        //When
        val response = movieUseCase.getMovie(1)

        //Then
        coVerify(exactly = 0) { movieRepository.getMovieFromDatabase(1) }
        assert(response == movie)
    }
}