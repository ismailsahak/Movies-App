package com.ismail.moviesapp.utils

import androidx.databinding.BindingAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ismail.moviesapp.R
import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.network.ApiService
import com.ismail.moviesapp.ui.main.MovieRecyclerViewAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.util.*

object BindingUtils {
    @BindingAdapter("adapter")
    @JvmStatic
    fun addMovieItems(recyclerView: RecyclerView, movies: ArrayList<Movie>) {
        val adapter: MovieRecyclerViewAdapter? = recyclerView.adapter as MovieRecyclerViewAdapter?
        adapter?.clearItems()
        adapter?.addItems(movies)
    }

    @BindingAdapter("poster")
    @JvmStatic
    fun setMoviePoster(imageView: ImageView, posterPath: String?) {
        GlideApp.with(imageView.context)
                .load(ApiService.POSTER_BASE_URL + posterPath)
                .error(R.drawable.tmdb_placeholder)
                .fallback(R.drawable.tmdb_placeholder)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}
