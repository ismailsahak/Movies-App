package com.ismail.moviesapp.ui.main

import androidx.lifecycle.ViewModelProvider
import com.ismail.moviesapp.ViewModelProviderFactory
import com.ismail.moviesapp.repository.MoviesRepository
import com.ismail.moviesapp.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun mainViewModel(moviesRepository: MoviesRepository): MainViewModel = MainViewModel(moviesRepository)

    @Provides
    fun provideMovieAdapter(): MovieRecyclerViewAdapter = MovieRecyclerViewAdapter(ArrayList())

    @Provides
    fun provideMainViewModel(mainViewModel: MainViewModel): ViewModelProvider.Factory = ViewModelProviderFactory(mainViewModel)
}