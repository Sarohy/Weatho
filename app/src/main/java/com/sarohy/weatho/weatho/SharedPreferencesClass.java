package com.sarohy.weatho.weatho;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.Nullable;

public class SharedPreferencesClass {
    private static Context context;

    public SharedPreferencesClass(Context context){
        this.context = context;
    }

    private static String SP_City = "WeatherInfo";
    private static String Key_CityKey = "CityKey";
    private static String Key_AutoUpdate = "autoUpdate";
    private static String Key_Temperature = "temperature";

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

    public void setAutoUpdate( String value) {
        android.content.SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Key_AutoUpdate, value);
        editor.apply();
    }

    public String getAutoUpdate() {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Key_AutoUpdate, "60");
    }
    public void setTemperature( String value) {
        android.content.SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        android.content.SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Key_Temperature, value);
        editor.apply();
    }

    public String getTemperature() {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Key_Temperature, "1");
    }
}
