package com.ismail.moviesapp.di.builder

import com.ismail.moviesapp.ui.details.DetailsActivity
import com.ismail.moviesapp.ui.details.DetailsActivityModule
import com.ismail.moviesapp.ui.main.MainActivity
import com.ismail.moviesapp.ui.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [DetailsActivityModule::class])
    abstract fun bindDetailsActivity(): DetailsActivity
}