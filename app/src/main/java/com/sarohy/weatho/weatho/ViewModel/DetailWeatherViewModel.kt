package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import com.sarohy.weatho.weatho.Model.ProjectRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailWeatherViewModel(application: Application) : AndroidViewModel(application) {
    private var weatherDay: MutableLiveData<List<WeatherHour>> = MutableLiveData()
    private var projectRepository: ProjectRepository = ProjectRepository()
    lateinit var cityKey:String
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

    fun setKey(key: String) {
        cityKey =key
        loadData()
    }

}