package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.Model.ProjectRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(application: Application,key:String) : AndroidViewModel(application) {
    private var weatherDay:MutableLiveData<List<WeatherDay>> = MutableLiveData()
    private var projectRepository: ProjectRepository = ProjectRepository(application)
    private var currentWeather: MutableLiveData<WeatherCurrent> = MutableLiveData()
    var disposableObserverWeatherCurrent: DisposableSingleObserver<WeatherCurrent>
    var cityKey:String = key

    init {
        loadData()
        disposableObserverWeatherCurrent =object : DisposableSingleObserver<WeatherCurrent>(){
            override fun onSuccess(t: WeatherCurrent) {
                currentWeather.value = t
            }

            override fun onError(e: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }

    private fun loadData() {

        projectRepository.loadDayForecastFromDB(cityKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ next->
                    weatherDay.value = next
                },
                {e->
                  e.printStackTrace()
                })
        projectRepository.loadCurrentWeatherFromDB(cityKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserverWeatherCurrent)
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
    fun disposeElements(){
        if(null != disposableObserverWeatherCurrent && !disposableObserverWeatherCurrent.isDisposed) disposableObserverWeatherCurrent.dispose()
    }

}