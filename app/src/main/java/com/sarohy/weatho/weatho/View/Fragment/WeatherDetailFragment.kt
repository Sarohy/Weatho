package com.sarohy.weatho.weatho.View.Fragment

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.View.Adapter.HourForecastRVAdapter
import com.sarohy.weatho.weatho.ViewModel.DetailWeatherViewModel
import com.sarohy.weatho.weatho.ViewModel.DetailWeatherViewModelFactory
import kotlinx.android.synthetic.main.fragment_detail_weather.view.*
import android.arch.lifecycle.Observer
import android.util.DisplayMetrics
import android.util.Log
import android.view.animation.Interpolator
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import kotlin.collections.ArrayList

class WeatherDetailFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var city: Location
    private lateinit var hourForecastRVAdapter: HourForecastRVAdapter
    private lateinit var viewModel:DetailWeatherViewModel
    private lateinit var rootView:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_detail_weather, container, false)
        init()
        setup()
        observers()
        animation()
        return rootView
    }

    private fun observers() {
        viewModel.getForecast().observe(context as LifecycleOwner, Observer<List<WeatherHour>> { fetch ->
            hourForecastRVAdapter.setArray(fetch)
            Log.d("Tested","Check")
            hourForecastRVAdapter.notifyDataSetChanged()
            rootView.root_layout.isRefreshing = false
        })
    }

    private fun init() {
        val factory = DetailWeatherViewModelFactory(activity?.application!!, city.key )
        viewModel = ViewModelProviders.of(this, factory).get(DetailWeatherViewModel::class.java)
        hourForecastRVAdapter = HourForecastRVAdapter(ArrayList())
    }

    private fun setup() {
        rootView.rv_hours_forecast.setHasFixedSize(true)
        rootView.root_layout.setOnRefreshListener(this)
        val mLayoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL, false)
        rootView.rv_hours_forecast.itemAnimator = DefaultItemAnimator()
        rootView.rv_hours_forecast.layoutManager = mLayoutManager
        rootView.rv_hours_forecast.adapter = hourForecastRVAdapter
    }

    companion object {
        fun newInstance(location: Location): WeatherDetailFragment {
            val fragment = WeatherDetailFragment()
            fragment.city = location
            return fragment
        }
    }
    override fun onRefresh() {
        viewModel.updateWeatherInfo()
    }

    private fun animation() {
        val metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        rootView.rv_hours_forecast.translationY = metrics.heightPixels.toFloat()
        val interpolator = Class.forName("android.view.animation.AnticipateOvershootInterpolator").newInstance() as Interpolator
        rootView.rv_hours_forecast.animate().setInterpolator(interpolator)
                .setDuration(1500)
                .setStartDelay(100)
                .translationYBy((-metrics.heightPixels).toFloat())
                .start()
    }


}

