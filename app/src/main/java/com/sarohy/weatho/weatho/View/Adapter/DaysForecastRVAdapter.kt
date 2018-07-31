package com.sarohy.weatho.weatho.View.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.R
import com.sarohy.weatho.weatho.Utils
import kotlinx.android.synthetic.main.item_day_forecast_list.view.*
import com.bumptech.glide.request.RequestOptions



class DaysForecastRVAdapter(citiesList: ArrayList<WeatherDay>, context: Activity) : RecyclerView.Adapter<DaysForecastRVAdapter.MyViewHolder>() {
    private lateinit var dataListAllItems: ArrayList<WeatherDay>
    private lateinit var context: Activity

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var temperature: TextView
        var date: TextView
        var dayImage:ImageView
        var nightImage:ImageView
        var dayPhrase:TextView
        var nightPhrase:TextView
        init {
            date = view.tv_forecast_date
            temperature = view.tv_forecast_temp
            dayImage = view.iv_day
            nightImage = view.iv_night
            dayPhrase = view.tv_day_phrase
            nightPhrase = view.tv_night_phrase
        }
    }


    init {
        this.context = context
        this.dataListAllItems = citiesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_day_forecast_list, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val weatherDay = dataListAllItems[position]
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val temperatureUnit = Integer.parseInt(prefs.getString("temperature", "1")!!)
        holder.temperature.text = Utils.showHiLowWeather(temperatureUnit,weatherDay.temperatureMax, weatherDay.temperatureMin,weatherDay.temperatureUnit)
        holder.date.text =  Utils.DateToString(weatherDay.date)
        holder.nightPhrase.text = weatherDay.iconPhraseNight
        holder.dayPhrase.text = weatherDay.iconPhraseDay
        val requestOptions1 = RequestOptions()
        requestOptions1.placeholder(Utils.getWeatherIcon(weatherDay.iconNight.toString()))
        val requestOptions2 = RequestOptions()
        requestOptions2.placeholder(Utils.getWeatherIcon(weatherDay.iconDay.toString()))
        Glide.with(context).load(Utils.getWeatherIconLink(weatherDay.iconNight.toString()))
                .apply(requestOptions1).into(holder.nightImage)
        Glide.with(context).load(Utils.getWeatherIconLink(weatherDay.iconDay.toString()))
               .apply(requestOptions2).into(holder.dayImage)
    }

    override fun getItemCount(): Int {
        return dataListAllItems.size
    }

    fun getItem(i: Int): WeatherDay {
        return dataListAllItems.get(i)
    }

    fun updateList(fetch: List<WeatherDay>) {
        dataListAllItems.clear()
        dataListAllItems.addAll(fetch)
    }
}