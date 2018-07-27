package com.sarohy.weatho.weatho.Model.APIModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DayForecastList {
    @SerializedName("DailyForecasts")
    private List<DayForecast> dayForecasts;

    public DayForecastList(List<DayForecast> dayForecasts) {
        this.dayForecasts = dayForecasts;
    }

    public List<DayForecast> getDayForecasts() {
        return dayForecasts;
    }

    public void setDayForecasts(List<DayForecast> dayForecasts) {
        this.dayForecasts = dayForecasts;
    }
}
