package com.sarohy.weatho.weatho.ViewModel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.FragmentActivity

class DetailWeatherViewModelFactory(application: Application, key:String) : ViewModelProvider.NewInstanceFactory() {
    private lateinit var application: Application
    private lateinit var key:String
    init {
        this.application =application
        this.key = key
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailWeatherViewModel(application, key) as T
    }
}