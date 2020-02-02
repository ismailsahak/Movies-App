package com.ismail.moviesapp.ui.main

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ismail.moviesapp.BR
import com.ismail.moviesapp.R
import com.ismail.moviesapp.databinding.ActivityMainBinding
import com.ismail.moviesapp.ui.base.BaseActivity
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {
    lateinit var mMainViewModel: MainViewModel
    @Inject
    lateinit var mAdapter: MovieRecyclerViewAdapter
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private var mActivityMainBinding: ActivityMainBinding? = null
    private var isLoading: Boolean = false

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getViewModel(): MainViewModel {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel::class.java)
        return mMainViewModel
    }

    @SuppressLint("RtlHardcoded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = viewDataBinding
        mMainViewModel.setNavigator(this)

        window.exitTransition = Slide(Gravity.LEFT)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        setupRecyclerView()
        addListener()
        subscribeToLiveData()
    }

    override fun handleError(throwable: Throwable) {
        longToast(resources.getString(R.string.error_messsage))
    }

    private fun addListener() {
        pullToRefreshLayout.setOnRefreshListener {
            mMainViewModel.clearMoviesList()
            mMainViewModel.fetchMovies(LATEST_MOVIES)
            pullToRefreshLayout.isRefreshing = false
        }
        attachMoviesOnScrollListener()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(applicationContext)
        moviesRecyclerView?.layoutManager = layoutManager
        moviesRecyclerView?.itemAnimator = LandingAnimator()
        moviesRecyclerView?.adapter = mAdapter
    }

    private fun attachMoviesOnScrollListener() {

        // Listener for pagination API call
        moviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = moviesRecyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (firstVisibleItem + visibleItemCount/2 >= totalItemCount && !isLoading) {
                    isLoading = true
                    moviesRecyclerView.removeOnScrollListener(this)
                    mMainViewModel.incrementPage()
                    mMainViewModel.fetchMovies(LATEST_MOVIES)
                }
            }
        })
    }

    private fun subscribeToLiveData() {
        mMainViewModel.observeMovieListLiveData().observe(this, Observer { it?.let {
            mMainViewModel.addMoviesToList(it)
            attachMoviesOnScrollListener()
        } })

        mMainViewModel.observeError().observe(this, Observer { it?.let {
            handleError(it)
        } })

        mMainViewModel.observeLoading().observe(this, Observer { it?.let {
            isLoading = it
        } })
    }

    companion object {
        const val LATEST_MOVIES = "latest_movies"
    }
}
