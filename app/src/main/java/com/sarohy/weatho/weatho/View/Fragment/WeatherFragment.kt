package com.sarohy.weatho.weatho.View.Fragment

import android.annotation.SuppressLint
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.Snackbar
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
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.RecyclerTouchListener
import com.sarohy.weatho.weatho.Utils
import com.sarohy.weatho.weatho.View.Activity.MainActivity
import com.sarohy.weatho.weatho.View.Activity.WeatherDeatilActivity
import com.sarohy.weatho.weatho.View.Adapter.DaysForecastRVAdapter
import com.sarohy.weatho.weatho.ViewModel.WeatherViewModel
import com.sarohy.weatho.weatho.ViewModel.WeatherViewModelFactory
import com.sarohy.weatho.weatho.WeathoApplication
import kotlinx.android.synthetic.main.fragment_weather.view.*
import java.util.*
import kotlin.collections.ArrayList


class WeatherFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener{


    private lateinit var city:Location
    private var weatherCurrent: WeatherCurrent?= null
    private lateinit var daysForecastRVAdapter:DaysForecastRVAdapter
    private lateinit var viewModel:WeatherViewModel
    private lateinit var rootView:View
    private lateinit var prefs:SharedPreferences
    private lateinit var parentActivity: MainActivity


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
                if (userVisibleHint)
                    parentActivity.sendDataToMain(weatherCurrent!!,city)
            }
        })
    }

    private fun init() {
        val factory = WeatherViewModelFactory(activity?.application!!, city.key)
        viewModel = ViewModelProviders.of(this, factory).get(WeatherViewModel::class.java)
        rootView.tv_current_date.text = Utils.DateToString(Date())
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
                    val i = Intent(activity, WeatherDeatilActivity::class.java)
                    i.putExtra("Location",city)
                    i.putExtra("CurrentWeather",weatherCurrent)
                    startActivity(i)
                }
                else{
                    var intent=Intent(Intent.ACTION_VIEW, Uri.parse(daysForecastRVAdapter.getItem(position).link))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.setPackage("com.android.chrome")
                    try {
                        context?.startActivity(intent);
                    } catch (ex: ActivityNotFoundException) {
                        // Chrome browser presumably not installed so allow user to choose instead
                        intent.setPackage(null)
                        context?.startActivity(intent)
                    }
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
        WeathoApplication.component.glide.load(Utils.getWeatherIconLink(weatherCurrent?.weatherIcon)).into(rootView.iv_weather_icon)
        val temperatureUnit = Integer.parseInt(WeathoApplication.component.sharedPrefs.temperatureUnit!!)
        rootView.tv_current_temp.text = Utils.showCurrentWeather(temperatureUnit,weatherCurrent?.temperature,weatherCurrent?.temperatureUnit)
        rootView.tv_phrase.text = weatherCurrent?.weatherText
        rootView.main_layout.isRefreshing = false
    }


    companion object {
        fun newInstance(location: Location,context: MainActivity): WeatherFragment {
            val fragment = WeatherFragment()
            fragment.city = location
            fragment.parentActivity = context
            return fragment
        }
    }
    override fun onRefresh() {
        if (isNetworkConnected()) {
            viewModel.updateWeatherInfo()
        }
        else{
            val snackBar = Snackbar.make(rootView, "No Internet connection!!.", Snackbar.LENGTH_SHORT)
            snackBar.setActionTextColor(getResources().getColor(R.color.white))
            snackBar.show()
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.getActiveNetworkInfo() != null
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser&& weatherCurrent!= null) {
            (parentActivity).sendDataToMain(weatherCurrent!!,city)
            Log.d("Tested","Visible"+city.key)
        } else {
        }
    }
    interface CallBack{
        fun sendDataToMain(weatherCurrent: WeatherCurrent, location: Location )
    }
}

