package com.sarohy.weatho.weatho.Model.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sarohy.weatho.weatho.Model.DBModel.WeatherDay;

import java.util.List;

@Dao
public interface WeatherDayDAO {

    @Query("SELECT * FROM WeatherDay where CityKey is  :cityKey ")
    LiveData<List<WeatherDay>> forecastByCity(String cityKey);

    @Insert
    void insertAll(WeatherDay... weatherDays);

    @Query("DELETE FROM WeatherDay Where CityKey is :cityKey")
    void deleteByCity(String cityKey);

    @Query("DELETE FROM WeatherDay")
    void deleteAll();
}
