package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class DetailWeatherViewModelFactory(application: Application, key:String) : ViewModelProvider.NewInstanceFactory() {
    private var application: Application = application
    private var key:String = key


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailWeatherViewModel(application, key) as T
    }
}