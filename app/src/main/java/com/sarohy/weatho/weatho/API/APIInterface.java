package com.sarohy.weatho.weatho.API;


import com.sarohy.weatho.weatho.Model.APIModel.City;
import com.sarohy.weatho.weatho.Model.APIModel.CurrentWeather;
import com.sarohy.weatho.weatho.Model.APIModel.HourForecast;
import com.sarohy.weatho.weatho.Model.APIModel.DayForecastList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@SuppressWarnings("SpellCheckingInspection")
public interface APIInterface {
    @GET("locations/v1/cities/autocomplete")
    Call<ArrayList<City>> getCities(@Query("apikey") String apiKey, @Query("q") String value);

    @GET("forecasts/v1/daily/5day/{id}")
    Call<DayForecastList> getForecastOf5Day(@Path("id") String cId, @Query("apikey") String apiKey);

    @GET("forecasts/v1/hourly/12hour/{id}")
    Call<ArrayList<HourForecast>> get12HoursForecast(@Path("id") String cId, @Query("apikey") String apiKey);

    @GET("currentconditions/v1/{id}")
    Call<ArrayList<CurrentWeather>> getCurrentUpdate(@Path("id") String cId, @Query("apikey") String apiKey);

    @GET("locations/v1/cities/geoposition/search")
    Call<City> getCity(@Query("apikey") String apiKey,@Query("q") String value);


}
