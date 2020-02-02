package com.ismail.moviesapp.ui.main


import androidx.databinding.ObservableField
import android.view.View
import android.widget.ImageView
import com.ismail.moviesapp.model.Movie

class MovieItemViewModel(private val mMovie: Movie, private val mListener: MovieItemViewModelListener) {

    val poster: ObservableField<String?> = ObservableField(mMovie.posterPath)
    val movieName: ObservableField<String?> = ObservableField(mMovie.title)
    val popularity: ObservableField<Float?> = ObservableField(mMovie.popularity)

    fun onItemClick(view: View) {
        mListener.onItemClick(mMovie, view as ImageView)
    }

    interface MovieItemViewModelListener {
        fun onItemClick(movie: Movie, imageView: ImageView)
    }
}
