package com.ismail.moviesapp.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}
