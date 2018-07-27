package com.sarohy.weatho.weatho.Model.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sarohy.weatho.weatho.Model.DBModel.WeatherHour;

import java.util.List;

@Dao
public interface WeatherHoursDAO {
    @Query("SELECT * FROM WeatherHour")
    List<WeatherHour> getAll();

//    @Query("SELECT * FROM WeatherDay where Date LIKE  :firstName AND last_name LIKE :lastName")
//    City findByName(String firstName, String lastName);


    @Query("SELECT * FROM WeatherHour where CityKey is  :cityKey")
    List<WeatherHour> forecastByCity(String cityKey);

    @Query("SELECT COUNT(*) from WeatherHour")
    int countCities();

    @Insert
    void insertAll(WeatherHour... weather12Hours);

    @Delete
    void delete(WeatherHour weather12Hour);

    @Query("DELETE FROM WeatherHour Where CityKey is :cityKey")
    void deleteByCity(String cityKey);
    @Query("DELETE FROM WeatherHour")
    void deleteAll();
}
