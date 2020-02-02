package com.ismail.moviesapp.di.module

import android.app.Application
import android.content.Context
import com.ismail.moviesapp.utils.rx.AppSchedulerProvider
import com.ismail.moviesapp.utils.rx.SchedulerProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ismail.moviesapp.BuildConfig
import com.ismail.moviesapp.repository.MoviesRepository
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
    fun provideMoviesRepository(): MoviesRepository = MoviesRepository()
}