package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import com.sarohy.weatho.weatho.Model.ProjectRepository

class DetailWeatherViewModel(application: Application, key:String) : AndroidViewModel(application) {
    private lateinit var weatherDay: LiveData<List<WeatherHour>>
    private var projectRepository: ProjectRepository = ProjectRepository(application)
    var cityKey:String = key

    init {
        loadData()
    }

    private fun loadData() {
        weatherDay = projectRepository.loadHourlyDataFromDB(cityKey)
    }


    fun getForecast(): LiveData<List<WeatherHour>> {
        return weatherDay
    }

    fun updateWeatherInfo() {
        projectRepository.fetchHourlyData(cityKey)
    }

}