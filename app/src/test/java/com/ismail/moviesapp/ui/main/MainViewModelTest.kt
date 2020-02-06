package com.ismail.moviesapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ismail.moviesapp.model.Genre
import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.model.TMDBResponse
import com.ismail.moviesapp.network.Resource
import com.ismail.moviesapp.repository.MoviesRepository
import com.ismail.moviesapp.ui.details.DetailsViewModel
import com.ismail.moviesapp.utils.Constants
import com.jraska.livedata.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val repository: MoviesRepository = mock()
    private val movieListResponse: MutableLiveData<Resource<TMDBResponse>> = MutableLiveData()

    @Before
    @Throws(Exception::class)
    fun setup() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun getMoviesObservableArrayList() {
    }

    @Test
    fun addMoviesToList() {
    }

    @Test
    fun clearMoviesList() {
    }

    @Test
    fun fetchMovies() {
        // Arrange
        val movieListObs = viewModel.observeMovieListLiveData().test()
        val currentPage = 1
        val firstMovie = Movie(null, "Sample title", "This is a plot", "02-11-1999", "3", 1, null, null, listOf(Genre(1, "Horror")), "English", 120)
        val secondMovie = Movie(null, "Sample title 2", "Movie 2 plot", "02-11-2012", "5", 12, null, null, listOf(Genre(1, "Mystery")), "English", 130)

        val tmdbResponse = TMDBResponse(currentPage, arrayListOf(firstMovie, secondMovie), 2, 1)

        whenever(repository.getMoviesByReleaseDate(1)).thenReturn(movieListResponse)
        movieListResponse.value = Resource.success(tmdbResponse)

        // Act
        viewModel.fetchMovies(MainActivity.LATEST_MOVIES)

        // Assert
        movieListObs.assertHasValue().assertValue { it == listOf(firstMovie, secondMovie) }
    }

    @Test
    fun observeError() {
        // Arrange
        val movieListObs = viewModel.observeMovieListLiveData().test()
        val errorObs = viewModel.observeError().test()

        whenever(repository.getMoviesByReleaseDate(1)).thenReturn(movieListResponse)
        movieListResponse.value = Resource.error(Throwable("Oops!"))

        // Act
        viewModel.fetchMovies(MainActivity.LATEST_MOVIES)

        // Assert
        movieListObs.assertNoValue()
        errorObs.assertHasValue().assertValue { it.message == "Oops!" }

    }

    @Test
    fun observeLoading() {
        // Arrange
        val loadingObs = viewModel.observeLoading().test()

        whenever(repository.getMoviesByReleaseDate(1)).thenReturn(movieListResponse)
        movieListResponse.value = Resource.loading(null)

        // Act
        viewModel.fetchMovies(MainActivity.LATEST_MOVIES)

        // Assert
        loadingObs.assertHasValue().assertValue { it == true }
    }
}