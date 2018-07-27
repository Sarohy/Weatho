package com.sarohy.weatho.weatho.View.Fragment

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_weather.view.*
import android.support.v4.app.Fragment
import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.transition.Slide
import android.view.*
import android.view.animation.Interpolator
import android.util.DisplayMetrics
import android.util.Log
import android.webkit.WebView
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.Utils
import com.sarohy.weatho.weatho.View.Activity.DetailWeatherActivity
import com.sarohy.weatho.weatho.View.Adapter.DaysForecastRVAdapter
import com.sarohy.weatho.weatho.ViewModel.WeatherViewModel
import com.sarohy.weatho.weatho.ViewModel.WeatherViewModelFactory
import kotlinx.android.synthetic.main.fragment_weather.*
import java.util.*
import kotlin.collections.ArrayList


class WeatherFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{



    public lateinit var city:Location
    public lateinit var weatherCurrent: WeatherCurrent;
    lateinit var daysForecastRVAdapter:DaysForecastRVAdapter;
    private lateinit var viewModel:WeatherViewModel
    lateinit var rootView:View
    private lateinit var prefs:SharedPreferences

    @SuppressLint("ResourceType", "PrivateApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_weather, container, false)
        prefs =PreferenceManager.getDefaultSharedPreferences(this.activity);
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
                updateUI(rootView);
            }
        })
    }

    private fun init() {
        val factory = WeatherViewModelFactory(activity?.application!!, city.key)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        rootView.tv_current_date.text = Utils.DateToString(Date());
        rootView.tv_location.text = city.localizedName
        daysForecastRVAdapter = DaysForecastRVAdapter(ArrayList<WeatherDay>(), activity!!);
    }

    private fun setup() {
        rootView.rv_day_forecast.setHasFixedSize(true)
        rootView.main_layout.setOnRefreshListener(this)
        val mLayoutManager = LinearLayoutManager(activity!!.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false)
        rootView.rv_day_forecast.setItemAnimator(DefaultItemAnimator())
        rootView.rv_day_forecast.setLayoutManager(mLayoutManager)
        rootView.rv_day_forecast.setAdapter(daysForecastRVAdapter)
        rootView.wv_weather.setOnClickListener(this)
    }

    private fun animation() {
        val metrics = DisplayMetrics()
        activity!!.getWindowManager().getDefaultDisplay().getMetrics(metrics)
        rootView.layout.setTranslationY(metrics.heightPixels.toFloat())
        val slide = Slide(Gravity.BOTTOM);
        slide.addTarget(rootView.tv_current_date);
        activity!!.window.enterTransition = slide
        val interpolator = Class.forName("android.view.animation.DecelerateInterpolator").newInstance() as Interpolator
        rootView.layout.animate().setInterpolator(interpolator)
                .setDuration(1500)
                .setStartDelay(100)
                .translationYBy((-metrics.heightPixels).toFloat())
                .start()
    }


    private fun updateUI(rootView: View) {
        val temperatureUnit = Integer.parseInt(prefs.getString("temperature", "1")!!)
        val webSetting = rootView.wv_weather.getSettings()
        webSetting.setJavaScriptEnabled(true)
        rootView.wv_weather.loadUrl("file:///android_asset/index.html")
        val s = Utils.showCurrentWeather(temperatureUnit, weatherCurrent.temperature,weatherCurrent.temperatureUnit)
        val str = "javascript:myJavaScriptFunc('"+s+"',"+Utils.mapOfWeather(weatherCurrent.weatherIcon)+",'"+weatherCurrent.weatherText+"')"
        Log.d("Tested",str)
        rootView.wv_weather.webViewClient = object : android.webkit.WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                rootView.wv_weather.loadUrl(str)
            }
        }
        rootView.main_layout.isRefreshing = false;
    }


    companion object {
        fun newInstance(location: Location): WeatherFragment {
            val fragment = WeatherFragment()
            fragment.city = location
            return fragment
        }
    }
    override fun onRefresh() {
        Log.d("SwipeCheck","Refresh")
        viewModel.updateWeatherInfo();
    }
    override fun onClick(v: View?) {
        if (v?.id==R.id.wv_weather){
            val i = Intent(activity,DetailWeatherActivity::class.java)
            i.putExtra("Location",city)
            startActivity(i)
        }
    }
}

