package com.ismail.moviesapp.ui.details

import androidx.lifecycle.ViewModelProvider
import com.ismail.moviesapp.ViewModelProviderFactory
import com.ismail.moviesapp.repository.MoviesRepository
import com.ismail.moviesapp.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides


@Module
class DetailsActivityModule {
    @Provides
    fun detailsViewModel(schedulerProvider: SchedulerProvider, moviesRepository: MoviesRepository): DetailsViewModel = DetailsViewModel(schedulerProvider, moviesRepository)

    @Provides
    fun provideDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModelProvider.Factory = ViewModelProviderFactory(detailsViewModel)
}