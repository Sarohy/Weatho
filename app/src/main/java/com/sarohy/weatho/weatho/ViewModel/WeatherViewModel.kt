package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.Model.ProjectRepository

class WeatherViewModel(application: Application,key:String) : AndroidViewModel(application) {
    private lateinit var weatherDay:LiveData<List<WeatherDay>>
    lateinit var projectRepository: ProjectRepository;
    private lateinit var currentWeather:LiveData<WeatherCurrent>
    lateinit var cityKey:String
    init {
        cityKey = key
        projectRepository = ProjectRepository(application)
        loadData()
    }

    private fun loadData() {
        weatherDay = projectRepository.loadDayForecastFromDB(cityKey)
        currentWeather = projectRepository.loadCurrentWeatherFromDB(cityKey)
    }

    public fun getWeatherDay(): LiveData<List<WeatherDay>>{
        return weatherDay
    }
    fun getCurrentWeather():LiveData<WeatherCurrent>{
        return currentWeather;
    }


    fun updateWeatherInfo() {
        projectRepository.loadAllData(cityKey)
        loadData()
    }

}