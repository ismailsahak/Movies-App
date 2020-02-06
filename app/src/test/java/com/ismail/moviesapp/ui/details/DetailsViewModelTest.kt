package com.ismail.moviesapp.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.ismail.moviesapp.model.Genre
import com.ismail.moviesapp.model.Movie
import com.jraska.livedata.test
import com.ismail.moviesapp.network.Resource
import com.ismail.moviesapp.repository.MoviesRepository
import org.junit.Assert.assertEquals
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: DetailsViewModel
    private val repository: MoviesRepository = mock()
    private val movieLiveData: MutableLiveData<Resource<Movie>> = MutableLiveData()

    @Before
    @Throws(Exception::class)
    fun setup() {
        viewModel = DetailsViewModel(repository)
    }

    @Test
    fun testGetMovie() {
        // Arrange
        val runtimeObs = viewModel.observeRuntime().test()
        val genreObs = viewModel.observeGenres().test()

        val movieId = 1
        val movie = Movie(null, "Sample title", "This is a plot", "02-11-1999", "3", 1, null, null, listOf(Genre(1, "Horror")), "English", 120)

        whenever(repository.getMovieById(1)).thenReturn(movieLiveData)
        movieLiveData.value = Resource.success(movie)

        // Act
        viewModel.getMovieById(movieId)

        // Assert
        assertEquals(viewModel.movieLiveData.value, movie)
        assertEquals(viewModel.isLoading.value, false)
        runtimeObs.assertHasValue().assertValue { it == "2 hours 0 minute" }
        genreObs.assertHasValue().assertValue { it == "Horror" }
    }

    @Test
    fun testNavigateToBooking() {
        // Arrange
        val navigateObs = viewModel.navigateToBooking.test()

        // Act
        viewModel.onClickBooking()

        // Assert
        navigateObs.assertHasValue()
    }


    @Test
    fun observeError() {
        // Arrange
        val errorObs = viewModel.observeError().test()
        val movieId = 1
        val movie = Movie(null, "Sample title", "This is a plot", "02-11-1999", "3", 1, null, null, listOf(Genre(1, "Horror")), "English", 120)

        whenever(repository.getMovieById(1)).thenReturn(movieLiveData)
        movieLiveData.value = Resource.error(Throwable("Error"))

        // Act
        viewModel.getMovieById(movieId)

        // Assert
        errorObs.assertHasValue().assertValue { it.message == "Error" }
    }
}

