package com.sarohy.weatho.weatho.Model.DBModel;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "WeatherCurrent",foreignKeys = @ForeignKey(entity = Location.class,
        parentColumns = "Key",
        childColumns = "CityKey", onDelete = CASCADE))
public class WeatherCurrent implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int key;

    @ColumnInfo(name = "CityKey")
    private String CityKey;

    @ColumnInfo(name = "WeatherIcon")
    private String WeatherIcon;

    @ColumnInfo(name = "WeatherText")
    private String WeatherText;

    @ColumnInfo(name = "MobileLink")
    private String MobileLink;

    @ColumnInfo(name = "IsDayTime")
    private String IsDayTime;

    @ColumnInfo(name = "LocalObservationDateTime" )
    private Date LocalObservationDateTime;

    @ColumnInfo(name = "Temperature")
    private String Temperature;

    @ColumnInfo(name = "TemperatureUnit")
    private String TemperatureUnit;


    public WeatherCurrent() {

    }

    public WeatherCurrent(int key, String cityKey, String weatherIcon, String weatherText,
                          String mobileLink, String isDayTime, Date localObservationDateTime,
                          String temperature, String temperatureUnit) {
        this.key = key;
        CityKey = cityKey;
        WeatherIcon = weatherIcon;
        WeatherText = weatherText;
        MobileLink = mobileLink;
        IsDayTime = isDayTime;
        LocalObservationDateTime = localObservationDateTime;
        Temperature = temperature;
        TemperatureUnit = temperatureUnit;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getCityKey() {
        return CityKey;
    }

    public void setCityKey(String cityKey) {
        CityKey = cityKey;
    }

    public String getWeatherIcon() {
        return WeatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        WeatherIcon = weatherIcon;
    }

    public String getWeatherText() {
        return WeatherText;
    }

    public void setWeatherText(String weatherText) {
        WeatherText = weatherText;
    }

    public String getMobileLink() {
        return MobileLink;
    }

    public void setMobileLink(String mobileLink) {
        MobileLink = mobileLink;
    }

    public String getIsDayTime() {
        return IsDayTime;
    }

    public void setIsDayTime(String isDayTime) {
        IsDayTime = isDayTime;
    }

    public Date getLocalObservationDateTime() {
        return LocalObservationDateTime;
    }

    public void setLocalObservationDateTime(Date localObservationDateTime) {
        LocalObservationDateTime = localObservationDateTime;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getTemperatureUnit() {
        return TemperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        TemperatureUnit = temperatureUnit;
    }
}

