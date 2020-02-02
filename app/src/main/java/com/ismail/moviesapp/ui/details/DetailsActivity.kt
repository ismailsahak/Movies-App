package com.ismail.moviesapp.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.appbar.AppBarLayout
import com.ismail.moviesapp.BR
import com.ismail.moviesapp.R
import com.ismail.moviesapp.databinding.ActivityDetailsBinding
import com.ismail.moviesapp.model.Movie
import com.ismail.moviesapp.network.ApiService
import com.ismail.moviesapp.ui.base.BaseActivity
import com.ismail.moviesapp.utils.Constants
import com.ismail.moviesapp.utils.GlideApp
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.longToast
import javax.inject.Inject
import kotlin.math.abs


class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>(), DetailsNavigator {
    lateinit var mViewModel: DetailsViewModel
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private var mMovie: Movie? = null
    private var mBinding: ActivityDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        onGetInputData()
        onBindData(clRoot)
        setupActionBar()
        setupUI()
        startPostponedEnterTransition()
        getMovieDetails()
        subscribeToLiveData()
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_details

    override fun handleError(throwable: Throwable) {
        longToast(resources.getString(R.string.error_messsage))
    }

    override fun getViewModel(): DetailsViewModel {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DetailsViewModel::class.java)
        return mViewModel
    }

    private fun onGetInputData() {
        val receivedIntent = intent
        if (receivedIntent.hasExtra(Constants.MOVIE)) {
            mMovie = receivedIntent.getParcelableExtra(Constants.MOVIE)
        }
    }

    private fun onBindData(view: View) {
        mBinding = DataBindingUtil.bind(view)
        mBinding?.lifecycleOwner = this
        mBinding?.viewmodel = mViewModel
    }

    private fun setupActionBar() {
        window.enterTransition = Slide(Gravity.BOTTOM)
        postponeEnterTransition()

        setSupportActionBar(detailsToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    private fun setupUI() {
        collapsingToolbar.title = mMovie?.title
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent))
        collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        appBar.addOnOffsetChangedListener(AppBarLayout.BaseOnOffsetChangedListener<AppBarLayout> { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout!!.totalScrollRange == 0)
                posterImageView!!.visibility = View.GONE
            else
                posterImageView!!.visibility = View.VISIBLE
        })
        loadImages()
    }

    private fun loadImages() {
        GlideApp.with(this)
                .load(ApiService.POSTER_BASE_URL + mMovie?.posterPath)
                .centerCrop()
                .into(posterImageView!!)
        GlideApp.with(this)
                .load(ApiService.BACKDROP_BASE_URL + mMovie?.backdropPath)
                .placeholder(R.drawable.tmdb_placeholder_land)
                .error(R.drawable.tmdb_placeholder_land)
                .fallback(R.drawable.tmdb_placeholder_land)
                .centerCrop()
                .into(backdropImageView!!)
    }

    private fun getMovieDetails() {
        let { mMovie?.let { mViewModel.getMovieById(it.id) } }
    }

    private fun subscribeToLiveData() {
        mViewModel.observeError().observe(this, Observer { it?.let {
            handleError(it)
        } })

        mViewModel.navigateToBooking.observe(this, Observer { it?.let {
            openBookingWebsite()
        }})
    }

    private fun openBookingWebsite() {
        val url = Constants.BOOKING_URL
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
