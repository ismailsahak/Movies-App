package com.ismail.moviesapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.network.Resource
import com.ismail.moviesapp.repository.MoviesRepository
import com.ismail.moviesapp.ui.base.BaseViewModel
import com.ismail.moviesapp.utils.rx.SchedulerProvider
import org.jetbrains.anko.collections.forEachWithIndex
import javax.inject.Inject

class DetailsViewModel @Inject constructor(schedulerProvider: SchedulerProvider, private val moviesRepository: MoviesRepository) : BaseViewModel<DetailsNavigator>(schedulerProvider) {
    val isLoading = MutableLiveData<Boolean>()
    val movieLiveData: MutableLiveData<Movie> = MutableLiveData()
    val navigateToBooking = MutableLiveData<Unit>()
    private val error = MutableLiveData<Throwable>()
    private val movieResponseObserver: Observer<Resource<Movie>> = Observer { t -> processMovieResponse(t) }


    private fun processMovieResponse(response: Resource<Movie>) {
        when(response.status) {
            Resource.Status.LOADING -> {
                isLoading.value = true
            }
            Resource.Status.SUCCESS -> {
                movieLiveData.value = response.data
                isLoading.value = false
            }
            Resource.Status.ERROR -> {
                error.value = response.error
                isLoading.value = false
            }
        }
    }

    fun getMovieById(movieId: Int) {
        moviesRepository.getMovieById(movieId).observeForever { movieResponseObserver.onChanged(it) }
    }

    fun observeError(): LiveData<Throwable> {
        return error
    }

    fun observeRuntime(): LiveData<String> {
        return Transformations.map(movieLiveData) {
            it.runtime?.let {duration ->
                if(duration == 0) {
                    return@map "Not specified yet"
                }
                val hours = duration / 60 //since both are ints, you get an int
                val minutes = duration % 60
                val hourText = if(hours > 1) "hours" else "hour"
                val minuteText = if(minutes > 1) "minutes" else "minute"
                return@map "$hours $hourText $minutes $minuteText"
            }
            return@map "Not specified yet"
        }
    }

    fun observeGenres(): LiveData<String> {
        return Transformations.map(movieLiveData) {
            val genres = it.genre
            val outputString: String = ""
            genres?.forEachWithIndex {index, genre ->
                outputString.plus(genre.name)
                if(index != genres.lastIndex){
                    outputString.plus(", ")
                }
            }
            return@map outputString
        }
    }

    fun onClickBooking() {
        navigateToBooking.value = Unit
    }

}