package com.ismail.moviesapp.repository

import androidx.lifecycle.LiveData
import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.model.TMDBResponse
import com.ismail.moviesapp.network.Resource

interface MoviesRepository {
    fun getMoviesByReleaseDate(currentPage: Int): LiveData<Resource<TMDBResponse>>
    fun getMovieById(movieId: Int): LiveData<Resource<Movie>>
}