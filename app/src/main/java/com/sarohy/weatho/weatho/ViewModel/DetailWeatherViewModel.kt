package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import com.sarohy.weatho.weatho.Model.ProjectRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailWeatherViewModel(application: Application) : AndroidViewModel(application) {
    private var weatherDay: MutableLiveData<List<WeatherHour>> = MutableLiveData()
    private var projectRepository: ProjectRepository = ProjectRepository()
    private lateinit var cityKey:String
    private lateinit var city:Location
    private lateinit var weatherCurrent: WeatherCurrent
    private fun loadData() {
        projectRepository.loadHourlyDataFromDB(cityKey)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe ({ next->
                    weatherDay.value = next
                },
                        {e->
                            e.printStackTrace()
                        })
    }


    fun getForecast(): LiveData<List<WeatherHour>> {
        return weatherDay
    }

    fun updateWeatherInfo() {
        projectRepository.fetchHourlyData(cityKey)
    }

    fun setCity(location:  Location) {
        city = location
        cityKey =location.key
        loadData()
    }
    fun setCurrentWeather(current: WeatherCurrent) {
        weatherCurrent = current
    }

    fun getLocation(): Location {
        return city
    }

    fun getCurrentWeather(): WeatherCurrent {
        return weatherCurrent
    }

}