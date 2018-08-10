package com.sarohy.weatho.weatho.Model

import android.annotation.SuppressLint
import android.util.Log
import com.sarohy.weatho.weatho.API.APIInterface
import com.sarohy.weatho.weatho.Database.AppDatabase
import com.sarohy.weatho.weatho.Model.APIModel.City
import com.sarohy.weatho.weatho.Model.APIModel.CurrentWeather
import com.sarohy.weatho.weatho.Model.APIModel.DayForecastList
import com.sarohy.weatho.weatho.Model.APIModel.HourForecast
import com.sarohy.weatho.weatho.Model.DBModel.Location
import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent
import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay
import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour
import com.sarohy.weatho.weatho.Utils
import com.sarohy.weatho.weatho.WeathoApplication
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ProjectRepository {
    private val LOG_TAG = ProjectRepository::class.java.simpleName

    @Inject
    lateinit var appDatabase: AppDatabase
    @Inject
    lateinit var apiService: APIInterface
    private val API_KEY: String = Utils.getAPIKey()

    init {
        WeathoApplication.component.injectRepo(this)
    }

    fun loadLocationFromDB(): Flowable<List<Location>>? {
        return appDatabase.locationDAO().getAll()
    }

    fun deleteLocation(city: Location) {
        val t = Thread(Runnable { appDatabase.locationDAO().delete(city) })
        t.start()

    }

    fun addLocation(city: Location) {
        val t = Thread(Runnable { appDatabase.locationDAO().insertAll(city) })
        t.start()

    }

    fun fetchLocationByLetter(str: String, callBack: CallBack) {
        apiService.getCities(API_KEY, str).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(Consumer<ArrayList<City>> { cities ->
                    callBack.onCityFetchedByWord(cities)
                    Log.d("Getting cities result", "Number of movies received: " + cities.size)
                }).subscribe()
    }

    private fun fetchDayForecast(cityKey: String) {
        apiService.getForecastOf5Day(cityKey, API_KEY).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .doOnNext(Consumer<DayForecastList> { dayForecastList ->
                    appDatabase.weatherDayDAO().deleteByCity(cityKey)
                    Log.d(LOG_TAG, "Inserting Day Weather Forecast")
                    for (d in dayForecastList.dayForecasts) {
                        appDatabase.weatherDayDAO().insertAll(Utils.weatherAPItoDB(cityKey, d))
                    }
                }).subscribe()
    }

    fun fetchHourlyData(cityKey: String) {

        apiService.get12HoursForecast(cityKey, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(Consumer<ArrayList<HourForecast>> { hourForecasts ->
                    appDatabase.weatherOf12HoursDAO().deleteByCity(cityKey)
                    Log.d(LOG_TAG, "Inserting Hour Weather Forecast")
                    for (d in hourForecasts) {
                        val weather12Hour = Utils.weatherOf12HoursAPItoDB(cityKey, d)
                        appDatabase.weatherOf12HoursDAO().insertAll(weather12Hour)
                    }
                }).subscribe()
    }

    @SuppressLint("CheckResult")
    private fun fetchCurrentWeather(cityKey: String) {
        apiService.getCurrentUpdate(cityKey, API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(Consumer<ArrayList<CurrentWeather>> { currentWeathers ->
                    appDatabase.currentWeatherDAO().deleteByCity(cityKey)
                    for (currentWeather in currentWeathers) {
                        val weatherCurrent = Utils.currentWeatherAPItoDB(cityKey, currentWeather)
                        updatePref(weatherCurrent)
                        appDatabase.currentWeatherDAO().insertAll(weatherCurrent)
                    }
                }).subscribe()
    }

    fun fetchLocationByGeo(location: String, callBack: CallBack) {

        apiService.getCity(API_KEY, location).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(Consumer<City> { city -> callBack.onLocationFetchedByGeo(Utils.cityAPItoDB(city)) }).subscribe()
    }

    fun loadDayForecastFromDB(cityKey: String): Flowable<List<WeatherDay>>? {
        return appDatabase.weatherDayDAO().forecastByCity(cityKey)
    }


    fun loadHourlyDataFromDB(cityKey: String): Flowable<List<WeatherHour>>? {
        return appDatabase.weatherOf12HoursDAO().forecastByCity(cityKey)
    }

    fun loadAllData(key: String) {
        fetchDayForecast(key)
        fetchCurrentWeather(key)
        fetchHourlyData(key)
    }

    @SuppressLint("CheckResult")
    fun loadDataSynchronous(locations: ArrayList<Location>): String {
        val arrayList = ArrayList<String>()
        for (i in locations.indices) {
            val cityKey = locations[i].key
            Log.d(LOG_TAG, "Doing Fetching")
            apiService.get12HoursForecast(cityKey, API_KEY).subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .doOnNext(Consumer<ArrayList<HourForecast>> { hourForecasts ->
                        if (hourForecasts.size >= 5) {
                            appDatabase.weatherOf12HoursDAO().deleteAll()
                            for (d in hourForecasts) {
                                appDatabase.weatherOf12HoursDAO().insertAll(Utils.weatherOf12HoursAPItoDB(cityKey, d))
                            }

                            Log.d(LOG_TAG, "Added to DB - hourly")
                        }
                    }).subscribe()


            apiService.getForecastOf5Day(cityKey, API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .doOnNext(Consumer<DayForecastList> { dayForecastList ->
                        appDatabase.weatherDayDAO().deleteAll()
                        for (d in dayForecastList.dayForecasts) {
                            val w = Utils.weatherAPItoDB(cityKey, d)
                            appDatabase.weatherDayDAO().insertAll(w)
                        }
                        val w = Utils.weatherAPItoDB(cityKey, dayForecastList.dayForecasts[0])
                        arrayList.add(locations[i].localizedName + " " + w.iconPhraseDay)
                        Log.d(LOG_TAG, "Added to DB - daily")
                    }).subscribe()
            apiService.getCurrentUpdate(cityKey, API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .doOnNext(Consumer<ArrayList<CurrentWeather>> { currentWeathers ->
                        if (currentWeathers != null) {
                            appDatabase.currentWeatherDAO().deleteAll()
                            val weatherCurrent = Utils.currentWeatherAPItoDB(
                                    cityKey, currentWeathers[0])
                            updatePref(weatherCurrent)
                            appDatabase.currentWeatherDAO().insertAll(weatherCurrent)
                            Log.d(LOG_TAG, "Added to DB - current")
                        }
                    }).subscribe()
        }
        Log.d(LOG_TAG, "Done")
        val str = StringBuilder()
        for (s in arrayList) {
            str.append(s).append("\n")
        }
        return str.toString()


    }
    private fun updatePref(weatherCurrent: WeatherCurrent) {
        val sharedPreferencesClass = WeathoApplication.component.sharedPrefs
        if (weatherCurrent.cityKey == sharedPreferencesClass.cityKey) {
            sharedPreferencesClass.currentTemp = weatherCurrent.temperature
            sharedPreferencesClass.currentTempUnit = weatherCurrent.temperatureUnit
            sharedPreferencesClass.setCurrentWeatherPhrase(weatherCurrent.weatherText)
            sharedPreferencesClass.lastUpdateTime = Utils.DateToTimeString(
                    weatherCurrent.localObservationDateTime)
            sharedPreferencesClass.weatherIcon = weatherCurrent.weatherIcon
        }
    }

    fun loadLocationFromDB(arrayList: ArrayList<Location>) {
        arrayList.addAll(appDatabase.locationDAO().getAllList()!!)
    }

    fun loadCurrentWeatherFromDB(cityKey: String): Flowable<WeatherCurrent>? {
        return appDatabase.currentWeatherDAO().forecastByCity(cityKey)
    }
    interface CallBack {
        fun onCityFetchedByWord(cities: ArrayList<City>)
        fun onLocationFetchedByGeo(location: Location)
    }
}