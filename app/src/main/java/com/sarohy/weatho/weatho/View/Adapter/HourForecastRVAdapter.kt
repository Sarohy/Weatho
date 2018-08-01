package com.sarohy.weatho.weatho.View.Adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.Utils
import com.sarohy.weatho.weatho.WeathoAppliccation
import kotlinx.android.synthetic.main.item_hourly_forecast_list.view.*

class HourForecastRVAdapter(citiesList: ArrayList<WeatherHour>) : RecyclerView.Adapter<HourForecastRVAdapter.MyViewHolder>() {
    private var dataListAllItems: ArrayList<WeatherHour> = citiesList

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var temperature: TextView
        var time: TextView
        var phrase: TextView
        var tempImange:ImageView
        init {
            time = view.tv_forecast_time
            temperature = view.tv_forecast_temperature
            phrase = view.tv_phrase
            tempImange = view.iv_weather_icon
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly_forecast_list, parent, false)

         return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherDay = dataListAllItems[position]
        val temperatureUnit = Integer.parseInt(WeathoAppliccation.component.sharedPrefs.temperature!!)
        holder.temperature.text = Utils.showCurrentWeather(temperatureUnit,weatherDay.temperature,weatherDay.temperatureUnit)
        holder.phrase.text = weatherDay.weatherText
        holder.time.text = Utils.DateToTime(weatherDay.localObservationDateTime)
        val requestOptions = RequestOptions()
        requestOptions.placeholder(Utils.getWeatherIcon(weatherDay.weatherIcon.toString()))
        WeathoAppliccation.component.glide.load(Utils.getWeatherIconLink(weatherDay.weatherIcon.toString()))
                .apply(requestOptions).into(holder.tempImange)
    }

    override fun getItemCount(): Int {
        return dataListAllItems.size
    }

    fun getItem(i: Int): WeatherHour {
        return dataListAllItems.get(i)
    }

    fun setArray(fetches: List<WeatherHour>?) {
        dataListAllItems.clear()
        dataListAllItems.addAll(fetches!!)
    }
}