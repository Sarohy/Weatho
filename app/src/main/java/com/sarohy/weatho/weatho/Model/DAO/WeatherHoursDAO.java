package com.sarohy.weatho.weatho.Model.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour;

import java.util.List;

@Dao
public interface WeatherHoursDAO {

    @Query("SELECT * FROM WeatherHour where CityKey is  :cityKey")
    LiveData<List<WeatherHour>> forecastByCity(String cityKey);

    @Insert
    void insertAll(WeatherHour... weather12Hours);

    @Query("DELETE FROM WeatherHour Where CityKey is :cityKey")
    void deleteByCity(String cityKey);

    @Query("DELETE FROM WeatherHour")
    void deleteAll();
}
