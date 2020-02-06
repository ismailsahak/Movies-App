package com.ismail.moviesapp.di.module

import android.app.Application
import android.content.Context
import com.ismail.moviesapp.utils.rx.AppSchedulerProvider
import com.ismail.moviesapp.utils.rx.SchedulerProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ismail.moviesapp.BuildConfig
import com.ismail.moviesapp.network.ApiService
import com.ismail.moviesapp.network.HttpClient
import com.ismail.moviesapp.repository.MoviesRepository
import com.ismail.moviesapp.repository.MoviesRepositoryImpl
import com.ismail.moviesapp.utils.NetworkUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApiKey(): String = BuildConfig.TMDB_API_TOKEN

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Singleton
    fun provideMoviesRepository(httpClient: HttpClient): MoviesRepository {
        return MoviesRepositoryImpl(httpClient)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return NetworkUtils
    }
}