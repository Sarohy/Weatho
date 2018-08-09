package com.sarohy.weatho.weatho.View.Activity

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.View.Adapter.HourForecastRVAdapter
import com.sarohy.weatho.weatho.ViewModel.DetailWeatherViewModel
import kotlinx.android.synthetic.main.activity_weather_deatil.*
import kotlinx.android.synthetic.main.content_weather_deatil.*


class WeatherDeatilActivity : AppCompatActivity() {

    lateinit var city: Location
    private lateinit var hourForecastRVAdapter: HourForecastRVAdapter
    private lateinit var viewModel:DetailWeatherViewModel
    private val LOG_TAG = WeatherDeatilActivity::class.java.simpleName + "Test: In Detail Fragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_deatil)
        setSupportActionBar(toolbar)
        fab_refresh.setOnClickListener { view ->
            onRefresh(view)
        }
        city = intent.extras.get("Location") as Location
        Log.d(LOG_TAG,""+city.key)
        init()
        setup()
        observers()
        animation()
    }

    private fun observers() {
        viewModel.getForecast().observe(this as LifecycleOwner, Observer<List<WeatherHour>> { fetch ->

            hourForecastRVAdapter.setArray(fetch)
            Log.d("Tested","Check")
            hourForecastRVAdapter.notifyDataSetChanged()
            //root_layout.isRefreshing = false
        })
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this as FragmentActivity).get<DetailWeatherViewModel>(DetailWeatherViewModel::class.java)
        viewModel.setKey(city.key)
        hourForecastRVAdapter = HourForecastRVAdapter(ArrayList())
    }

    private fun setup() {
        rv_hours_forecast.setHasFixedSize(true)
        //root_layout.setOnRefreshListener(this)
        val mLayoutManager = LinearLayoutManager(this.applicationContext, LinearLayoutManager.VERTICAL, false)
        rv_hours_forecast.itemAnimator = DefaultItemAnimator()
        rv_hours_forecast.layoutManager = mLayoutManager
        rv_hours_forecast.adapter = hourForecastRVAdapter
    }


    fun onRefresh(view: View) {
        if (isNetworkConnected()) {
            val interpolator = OvershootInterpolator()
            ViewCompat.animate(fab_refresh).rotation(135f).withLayer().setDuration(3000).setInterpolator(interpolator).start()
            viewModel.updateWeatherInfo()
        }
        else{

            val snackBar = Snackbar.make(view, "No Internet connection!!.", Snackbar.LENGTH_SHORT)
            snackBar.setActionTextColor(resources.getColor(R.color.white))
            snackBar.show()
        }
    }

    private fun animation() {
        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        rv_hours_forecast.translationY = metrics.heightPixels.toFloat()
        val interpolator = Class.forName("android.view.animation.AnticipateOvershootInterpolator").newInstance() as Interpolator
        rv_hours_forecast.animate().setInterpolator(interpolator)
                .setDuration(1500)
                .setStartDelay(100)
                .translationYBy((-metrics.heightPixels).toFloat())
                .start()
    }

    private fun isNetworkConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.getActiveNetworkInfo() != null
    }
}
