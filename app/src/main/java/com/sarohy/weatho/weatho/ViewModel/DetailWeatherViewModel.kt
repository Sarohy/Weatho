package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import com.sarohy.weatho.weatho.Model.ProjectRepository

class DetailWeatherViewModel(application: Application, key:String) : AndroidViewModel(application) {
    lateinit var weatherDay: LiveData<List<WeatherHour>>
    lateinit var projectRepository: ProjectRepository;
    lateinit var cityKey:String
    init {
        cityKey = key
        projectRepository = ProjectRepository(application)
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