package com.sarohy.weatho.weatho.Model.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sarohy.weatho.weatho.Model.DBModel.WeatherCurrent;

@Dao
public interface CurrentWeatherDAO {
    @Query("SELECT * FROM WeatherCurrent where CityKey is  :cityKey")
    LiveData<WeatherCurrent> forecastByCity(String cityKey);

    @Insert
    void insertAll(WeatherCurrent... currentWeatherUpdates);

    @Query("DELETE FROM WeatherCurrent Where CityKey is :cityKey")
    void deleteByCity(String cityKey);

    @Query("DELETE FROM WeatherCurrent")
    void deleteAll();
}
