package com.ismail.moviesapp.network

import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.model.TMDBResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by bapspatil
 */

interface ApiService {

    @GET("discover/movie")
    fun getMovies(@Query("sort_by") SORT_BY: String, @Query("api_key") API_KEY: String, @Query("language") LANGUAGE: String, @Query("page") PAGE: Int): Call<TMDBResponse>

    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") ID: Int, @Query("api_key") API_KEY: String): Call<Movie>

    companion object {

        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val BASE_URL = "https://api.themoviedb.org/3/"

        fun create(): ApiService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }
}
