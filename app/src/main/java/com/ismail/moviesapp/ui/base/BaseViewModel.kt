package com.ismail.moviesapp.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

import com.ismail.moviesapp.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>(schedulerProvider: SchedulerProvider) : ViewModel() {

    private val isLoading = ObservableBoolean(false)
    var mSchedulerProvider: SchedulerProvider = schedulerProvider
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var mNavigator: WeakReference<N>

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    fun getNavigator(): N? = mNavigator.get()

    fun setNavigator(navigator: N) {
        this.mNavigator = WeakReference(navigator)
    }
}
