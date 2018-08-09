package com.sarohy.weatho.weatho;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        //CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        SharedPreferencesClass sharedPreferencesClass = WeathoApplication.component.getSharedPrefs();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);
        views.setTextViewText(R.id.tv_current_temp, Utils.showCurrentWeather(
                Integer.valueOf(sharedPreferencesClass.getTemperatureUnit()),
                sharedPreferencesClass.getCurrentTemp(),
                sharedPreferencesClass.getCurrentTempUnit()));
        views.setTextViewText(R.id.tv_weather_phrase, sharedPreferencesClass.getCurrentWeatherPhrase());
        views.setTextViewText(R.id.tv_location, sharedPreferencesClass.getCityName());
        views.setTextViewText(R.id.tv_last_update, Utils.getLastUpdateTime(sharedPreferencesClass.getLastUpdateTime()));
        views.setImageViewResource(R.id.iv_weather_icon,Utils.getWeatherIcon(sharedPreferencesClass.getWeatherIcon()));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        Log.d("Tested", String.valueOf(appWidgetId));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

