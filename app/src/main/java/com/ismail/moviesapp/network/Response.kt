package com.ismail.moviesapp.network

class Resource<T> private constructor(val status: Status, val data: T?, val error: Throwable?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(error: Throwable?): Resource<T> {
            return Resource(Status.ERROR, null, error)
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}