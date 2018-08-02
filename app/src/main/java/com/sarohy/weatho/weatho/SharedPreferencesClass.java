package com.sarohy.weatho.weatho;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;

public class SharedPreferencesClass {
    private static Context context;


    public SharedPreferencesClass(Context context){
        SharedPreferencesClass.context = context;
    }

    private final String SP_City = "WeatherInfo";
    private final String SP_CurrentWeather = "CurrentWeatherInfo";
    private final String Key_CityKey = "CityKey";
    private final String Key_CityName = "CityName";
    private final String Key_CurrentTemp = "CurrentTemp";
    private final String Key_CurrentTempUnit = "CurrentTempUnit";
    private final String Key_CurrentPhrase = "CurrentPhrase";
    private final String Key_LastModified = "LastModified";
    private final String Key_CurrentWeatherIcon = "CurrentWeatherIcon";

    public void setCityKey( String value) {
        android.content.SharedPreferences sharedPref = context.getSharedPreferences(SP_City,Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Key_CityKey, value);
        editor.apply();
    }

    public String getCityKey() {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_City, Context.MODE_PRIVATE);
        return prefs.getString(Key_CityKey, "-1");
    }

    public String getAutoUpdate() {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String key_AutoUpdate = "autoUpdate";
        return prefs.getString(key_AutoUpdate, "60");
    }

    public String getTemperatureUnit() {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String key_Temperature = "temperature";
        return prefs.getString(key_Temperature, "1");
    }

    public String getCurrentTemp() {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        return prefs.getString(Key_CurrentTemp, "32");
    }

    public String getCurrentTempUnit() {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        return prefs.getString(Key_CurrentTempUnit, "C");
    }

    public CharSequence getCurrentWeatherPhrase() {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        return prefs.getString(Key_CurrentPhrase, "Clear");
    }

    public CharSequence getCityName() {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_City, Context.MODE_PRIVATE);
        return prefs.getString(Key_CityName, "----");
    }

    public String getLastUpdateTime() {
        Date date = Calendar.getInstance().getTime();
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        return prefs.getString(Key_LastModified, Utils.DateToString(date));
    }

    public String getWeatherIcon() {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        return prefs.getString(Key_CurrentWeatherIcon, "2");
    }


    public void setCurrentTemp(String value) {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Key_CurrentTemp, value);
        editor.apply();
    }

    public void setCurrentTempUnit(String value) {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Key_CurrentTempUnit, value);
        editor.apply();
    }

    public void setCurrentWeatherPhrase(String value) {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Key_CurrentPhrase, value);
        editor.apply();
    }

    public void setCityName(String value) {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_City, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Key_CityName, value);
        editor.apply();
    }

    public void setLastUpdateTime(String value) {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Key_LastModified, value);
        editor.apply();
    }

    public void setWeatherIcon(String value) {
        android.content.SharedPreferences prefs = context.getSharedPreferences(SP_CurrentWeather, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Key_CurrentWeatherIcon, value);
        editor.apply();
    }
}
