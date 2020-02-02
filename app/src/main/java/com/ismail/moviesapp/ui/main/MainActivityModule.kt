package com.ismail.moviesapp.ui.main

import androidx.lifecycle.ViewModelProvider
import com.ismail.moviesapp.ViewModelProviderFactory
import com.ismail.moviesapp.repository.MoviesRepository
import com.ismail.moviesapp.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun mainViewModel(schedulerProvider: SchedulerProvider, moviesRepository: MoviesRepository): MainViewModel = MainViewModel(schedulerProvider, moviesRepository)

    @Provides
    fun provideMovieAdapter(): MovieRecyclerViewAdapter = MovieRecyclerViewAdapter(ArrayList())

    @Provides
    fun provideMainViewModel(mainViewModel: MainViewModel): ViewModelProvider.Factory = ViewModelProviderFactory(mainViewModel)
}