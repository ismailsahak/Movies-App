package com.ismail.moviesapp.ui.main

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.model.TMDBResponse
import com.ismail.moviesapp.network.Resource
import com.ismail.moviesapp.repository.MoviesRepository
import com.ismail.moviesapp.ui.base.BaseViewModel
import com.ismail.moviesapp.utils.Constants
import com.ismail.moviesapp.utils.rx.SchedulerProvider
import javax.inject.Inject

class MainViewModel @Inject constructor(schedulerProvider: SchedulerProvider, private val moviesRepository: MoviesRepository) : BaseViewModel<MainNavigator>(schedulerProvider) {
    private var currentPage: Int = Constants.INITIAL_PAGE
    private val isLoading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Throwable>()
    val moviesObservableArrayList: ObservableArrayList<Movie> = ObservableArrayList()
    var movieListLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    private val movieListResponseObserver: Observer<Resource<TMDBResponse>> = Observer { t -> processMovieListResponse(t) }

    init {
        fetchMovies(MainActivity.LATEST_MOVIES)
    }

    fun addMoviesToList(movies: List<Movie>?) {
        movies?.let { moviesObservableArrayList.addAll(movies) }
    }

    fun clearMoviesList() {
        currentPage = 1
        moviesObservableArrayList.clear()
    }

    private fun processMovieListResponse(response: Resource<TMDBResponse>) {
        when(response.status) {
            Resource.Status.LOADING -> {
                isLoading.value = true
            }
            Resource.Status.SUCCESS -> {
                isLoading.value = false
                movieListLiveData.value = response.data?.results
            }
            Resource.Status.ERROR -> {
                isLoading.value = false
                error.value = response.error
            }
        }
    }

    fun fetchMovies(taskId: String?) {
        when (taskId) {
            MainActivity.LATEST_MOVIES -> moviesRepository.getMoviesByReleaseDate(currentPage).observeForever { movieListResponseObserver.onChanged(it) }
        }
    }

    fun incrementPage() {
        currentPage++
    }

    fun observeMovieListLiveData(): LiveData<List<Movie>> {
        return movieListLiveData
    }

    fun observeError(): LiveData<Throwable> {
        return error
    }

    fun observeLoading(): LiveData<Boolean> {
        return isLoading
    }
}
