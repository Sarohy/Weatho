package com.sarohy.weatho.weatho;

import android.content.Context;
import android.preference.PreferenceManager;

public class SharedPreferencesClass {
    private static Context context;

    public SharedPreferencesClass(Context context){
        SharedPreferencesClass.context = context;
    }

    private static final String SP_City = "WeatherInfo";
    private static final String Key_CityKey = "CityKey";
    private static final String Key_AutoUpdate = "autoUpdate";
    private static final String Key_Temperature = "temperature";

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
        return prefs.getString(Key_AutoUpdate, "60");
    }

    public String getTemperature() {
        android.content.SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Key_Temperature, "1");
    }
}
