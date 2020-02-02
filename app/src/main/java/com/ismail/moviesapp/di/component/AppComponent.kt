package com.ismail.moviesapp.di.component

import android.app.Application
import com.ismail.moviesapp.MoviesApp
import com.ismail.moviesapp.di.builder.ActivityBuilder
import com.ismail.moviesapp.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {

    fun inject(moviesApp: MoviesApp): MoviesApp

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}