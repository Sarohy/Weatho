package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import com.sarohy.weatho.weatho.ProjectRepository

class DetailWeatherViewModel(application: Application, key:String) : AndroidViewModel(application) {
    var weatherDay:MutableLiveData<ArrayList<WeatherHour>> = MutableLiveData()
    lateinit var projectRepository:ProjectRepository;
    lateinit var cityKey:String
    init {
        cityKey = key
        projectRepository = ProjectRepository.getInstance(application)
        loadData()
    }

    private fun loadData() {
        projectRepository.loadHourlyDataFromDB(cityKey,weatherDay)
    }


    fun getForecast(): MutableLiveData<ArrayList<WeatherHour>> {
        return weatherDay
    }

    fun updateWeatherInfo() {
        projectRepository.fetchHourlyData(cityKey,weatherDay)
        //loadData()
    }

}