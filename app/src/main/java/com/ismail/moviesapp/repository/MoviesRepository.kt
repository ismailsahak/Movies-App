package com.ismail.moviesapp.repository

import androidx.lifecycle.LiveData
import com.ismail.moviesapp.BuildConfig
import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.model.TMDBResponse
import com.ismail.moviesapp.network.ApiService
import com.ismail.moviesapp.network.NetworkCall
import com.ismail.moviesapp.network.Resource
import com.ismail.moviesapp.utils.Constants

class MoviesRepository {

    private val getMoviesCall = NetworkCall<TMDBResponse>()
    private val getMovieCall = NetworkCall<Movie>()

    private fun getApiService(): ApiService {
        return ApiService.create()
    }

    fun getMoviesByReleaseDate(currentPage: Int): LiveData<Resource<TMDBResponse>> {
        return getMoviesCall.makeCall(getApiService().getMovies(Constants.SORT_BY_RELEASE_DESC, BuildConfig.TMDB_API_TOKEN, "en-US", currentPage))
    }

    fun getMovieById(movieId: Int): LiveData<Resource<Movie>> {
        return getMovieCall.makeCall(getApiService().getMovie(movieId, BuildConfig.TMDB_API_TOKEN))
    }
}