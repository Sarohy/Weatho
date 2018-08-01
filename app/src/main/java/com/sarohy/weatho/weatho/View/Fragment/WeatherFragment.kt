package com.sarohy.weatho.weatho.View.Fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.transition.Slide
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.webkit.WebView
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.RecyclerTouchListener
import com.sarohy.weatho.weatho.Utils
import com.sarohy.weatho.weatho.View.Activity.DetailWeatherActivity
import com.sarohy.weatho.weatho.View.Adapter.DaysForecastRVAdapter
import com.sarohy.weatho.weatho.ViewModel.WeatherViewModel
import com.sarohy.weatho.weatho.ViewModel.WeatherViewModelFactory
import kotlinx.android.synthetic.main.fragment_weather.view.*
import java.util.*
import kotlin.collections.ArrayList


class WeatherFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener{


    private lateinit var city:Location
    private lateinit var weatherCurrent: WeatherCurrent
    private lateinit var daysForecastRVAdapter:DaysForecastRVAdapter
    private lateinit var viewModel:WeatherViewModel
    private lateinit var rootView:View
    private lateinit var prefs:SharedPreferences

    @SuppressLint("ResourceType", "PrivateApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_weather, container, false)
        prefs =PreferenceManager.getDefaultSharedPreferences(this.activity)
        animation()
        init()
        setup()
        observers()
        return rootView
    }

    private fun observers() {
        viewModel.getWeatherDay().observe(context as LifecycleOwner, Observer<List<WeatherDay>> { fetch ->
            if (fetch!=null) {
                daysForecastRVAdapter.updateList(fetch)
                daysForecastRVAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getCurrentWeather().observe(context as LifecycleOwner, Observer<WeatherCurrent> { fetch ->
            if (fetch!=null) {
                weatherCurrent = fetch
                updateUI(rootView)
            }
        })
    }

    private fun init() {
        val factory = WeatherViewModelFactory(activity?.application!!, city.key)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        rootView.tv_current_date.text = Utils.DateToString(Date())
        rootView.tv_location.text = city.localizedName
        daysForecastRVAdapter = DaysForecastRVAdapter(ArrayList(), activity!!)
    }

    private fun setup() {
        rootView.rv_day_forecast.setHasFixedSize(true)
        rootView.main_layout.setOnRefreshListener(this)
        val mLayoutManager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rootView.rv_day_forecast.itemAnimator = DefaultItemAnimator()
        rootView.rv_day_forecast.layoutManager = mLayoutManager
        rootView.rv_day_forecast.adapter = daysForecastRVAdapter
        rootView.rv_day_forecast.addOnItemTouchListener(RecyclerTouchListener(context, rootView.rv_day_forecast, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View, position: Int) {
                if (position==0){
                    val i = Intent(activity,DetailWeatherActivity::class.java)
                    i.putExtra("Location",city)
                    startActivity(i)
                }
            }

            override fun onLongClick(view: View, position: Int) {

            }
        }))
    }

    private fun animation() {
        val metrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(metrics)
        rootView.layout.translationY = metrics.heightPixels.toFloat()
        val slide = Slide(Gravity.BOTTOM)
        slide.addTarget(rootView.tv_current_date)
        activity!!.window.enterTransition = slide
        val interpolator = Class.forName("android.view.animation.DecelerateInterpolator").newInstance() as Interpolator
        rootView.layout.animate().setInterpolator(interpolator)
                .setDuration(1500)
                .setStartDelay(100)
                .translationYBy((-metrics.heightPixels).toFloat())
                .start()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun updateUI(rootView: View) {
        val temperatureUnit = Integer.parseInt(prefs.getString("temperature", "1")!!)
        val webSetting = rootView.wv_weather.settings
        webSetting.javaScriptEnabled = true
        rootView.wv_weather.loadUrl("file:///android_asset/index.html")
        val s = Utils.showCurrentWeather(temperatureUnit, weatherCurrent.temperature,weatherCurrent.temperatureUnit)
        val str = "javascript:myJavaScriptFunc('"+s+"',"+Utils.mapOfWeather(weatherCurrent.weatherIcon)+",'"+weatherCurrent.weatherText+"')"
        Log.d("Tested",str)
        rootView.wv_weather.webViewClient = object : android.webkit.WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                rootView.wv_weather.loadUrl(str)
            }
        }
        rootView.main_layout.isRefreshing = false
    }


    companion object {
        fun newInstance(location: Location): WeatherFragment {
            val fragment = WeatherFragment()
            fragment.city = location
            return fragment
        }
    }
    override fun onRefresh() {
        viewModel.updateWeatherInfo()
    }
}

