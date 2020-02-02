package com.ismail.moviesapp.di.builder

import com.ismail.moviesapp.ui.details.DetailsActivity
import com.ismail.moviesapp.ui.details.DetailsActivityModule
import com.ismail.moviesapp.ui.main.MainActivity
import com.ismail.moviesapp.ui.main.MainActivityModule
import com.ismail.moviesapp.ui.splash.SplashActivity
import com.ismail.moviesapp.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [DetailsActivityModule::class])
    abstract fun bindDetailsActivity(): DetailsActivity
}