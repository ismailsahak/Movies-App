package com.ismail.moviesapp.repository

import androidx.lifecycle.LiveData
import com.ismail.moviesapp.BuildConfig
import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.model.TMDBResponse
import com.ismail.moviesapp.network.ApiService
import com.ismail.moviesapp.network.HttpClient
import com.ismail.moviesapp.network.NetworkCall
import com.ismail.moviesapp.network.Resource
import com.ismail.moviesapp.utils.Constants
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val httpClient: HttpClient): MoviesRepository {

    private val getMoviesCall = NetworkCall<TMDBResponse>()
    private val getMovieCall = NetworkCall<Movie>()

    private fun getApiService(): ApiService {
        return httpClient.getApiService()
    }

    override fun getMoviesByReleaseDate(currentPage: Int): LiveData<Resource<TMDBResponse>> {
        return getMoviesCall.makeCall(getApiService().getMovies(Constants.SORT_BY_RELEASE_DESC, BuildConfig.TMDB_API_TOKEN, "en-US", currentPage))
    }

    override fun getMovieById(movieId: Int): LiveData<Resource<Movie>> {
        return getMovieCall.makeCall(getApiService().getMovie(movieId, BuildConfig.TMDB_API_TOKEN))
    }
}