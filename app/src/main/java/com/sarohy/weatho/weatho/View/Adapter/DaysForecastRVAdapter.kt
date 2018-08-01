package com.sarohy.weatho.weatho.View.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.Utils
import kotlinx.android.synthetic.main.item_day_forecast_list.view.*
import com.bumptech.glide.request.RequestOptions
import com.sarohy.weatho.weatho.WeathoApplication


class DaysForecastRVAdapter(citiesList: ArrayList<WeatherDay>, context: Activity) : RecyclerView.Adapter<DaysForecastRVAdapter.MyViewHolder>() {
    private var dataListAllItems: ArrayList<WeatherDay> = citiesList

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var temperature: TextView = view.tv_forecast_temp
        var date: TextView = view.tv_forecast_date
        var dayImage:ImageView = view.iv_day
        var nightImage:ImageView
        var dayPhrase:TextView
        var nightPhrase:TextView
        init {
            nightImage = view.iv_night
            dayPhrase = view.tv_day_phrase
            nightPhrase = view.tv_night_phrase
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_day_forecast_list, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherDay = dataListAllItems[position]
        val temperatureUnit = Integer.parseInt(WeathoApplication.component.sharedPrefs.temperature!!)
        holder.temperature.text = Utils.showHiLowWeather(temperatureUnit,weatherDay.temperatureMax, weatherDay.temperatureMin,weatherDay.temperatureUnit)
        holder.date.text =  Utils.DateToString(weatherDay.date)
        holder.nightPhrase.text = weatherDay.iconPhraseNight
        holder.dayPhrase.text = weatherDay.iconPhraseDay
        val requestOptions1 = RequestOptions()
        requestOptions1.placeholder(Utils.getWeatherIcon(weatherDay.iconNight.toString()))
        val requestOptions2 = RequestOptions()
        requestOptions2.placeholder(Utils.getWeatherIcon(weatherDay.iconDay.toString()))
        WeathoApplication.component.glide.load(Utils.getWeatherIconLink(weatherDay.iconNight.toString()))
                .apply(requestOptions1).into(holder.nightImage)
        WeathoApplication.component.glide.load(Utils.getWeatherIconLink(weatherDay.iconDay.toString()))
               .apply(requestOptions2).into(holder.dayImage)
    }

    override fun getItemCount(): Int {
        return dataListAllItems.size
    }

    fun updateList(fetch: List<WeatherDay>) {
        dataListAllItems.clear()
        dataListAllItems.addAll(fetch)
    }
}