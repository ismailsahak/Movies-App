package com.ismail.moviesapp.ui.details

import androidx.lifecycle.ViewModelProvider
import com.ismail.moviesapp.ViewModelProviderFactory
import com.ismail.moviesapp.repository.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides


@Module
class DetailsActivityModule {
    @Provides
    fun detailsViewModel(moviesRepository: MoviesRepositoryImpl): DetailsViewModel = DetailsViewModel(moviesRepository)

    @Provides
    fun provideDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModelProvider.Factory = ViewModelProviderFactory(detailsViewModel)
}