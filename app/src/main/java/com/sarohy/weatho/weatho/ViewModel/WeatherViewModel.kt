package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.Model.ProjectRepository

class WeatherViewModel(application: Application,key:String) : AndroidViewModel(application) {
    private lateinit var weatherDay:LiveData<List<WeatherDay>>
    private var projectRepository: ProjectRepository = ProjectRepository(application)
    private lateinit var currentWeather:LiveData<WeatherCurrent>
    var cityKey:String = key

    init {
        loadData()
    }

    private fun loadData() {
        weatherDay = projectRepository.loadDayForecastFromDB(cityKey)
        currentWeather = projectRepository.loadCurrentWeatherFromDB(cityKey)
    }

    fun getWeatherDay(): LiveData<List<WeatherDay>>{
        return weatherDay
    }
    fun getCurrentWeather():LiveData<WeatherCurrent>{
        return currentWeather
    }


    fun updateWeatherInfo() {
        projectRepository.loadAllData(cityKey)
        loadData()
    }

}