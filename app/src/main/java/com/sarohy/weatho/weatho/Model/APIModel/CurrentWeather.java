package com.sarohy.weatho.weatho.Model.APIModel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CurrentWeather {
    @SerializedName("WeatherIcon")
    private String WeatherIcon;

    @SerializedName("WeatherText")
    private String WeatherText;

    @SerializedName("MobileLink")
    private String MobileLink;

    @SerializedName("Temperature")
    private CurrentTemperature Temperature;

    @SerializedName("IsDayTime")
    private String IsDayTime;

    @SerializedName("LocalObservationDateTime")
    private String LocalObservationDateTime;

    @SerializedName("PrecipitationProbability")


    public String getWeatherIcon ()
    {
        return WeatherIcon;
    }

    public void setWeatherIcon (String WeatherIcon)
    {
        this.WeatherIcon = WeatherIcon;
    }

    public String getWeatherText ()
    {
        return WeatherText;
    }

    public void setWeatherText (String WeatherText)
    {
        this.WeatherText = WeatherText;
    }

    public String getMobileLink ()
    {
        return MobileLink;
    }

    public void setMobileLink (String MobileLink)
    {
        this.MobileLink = MobileLink;
    }

    public CurrentTemperature getTemperature ()
    {
        return Temperature;
    }

    public void setTemperature (CurrentTemperature Temperature)
    {
        this.Temperature = Temperature;
    }

    public String getIsDayTime ()
    {
        return IsDayTime;
    }

    public void setIsDayTime (String IsDayTime)
    {
        this.IsDayTime = IsDayTime;
    }

    public String getLocalObservationDateTime ()
    {
        return LocalObservationDateTime;
    }

    public void setLocalObservationDateTime (String LocalObservationDateTime)
    {
        this.LocalObservationDateTime = LocalObservationDateTime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [WeatherIcon = "+WeatherIcon+", WeatherText = "+WeatherText+", MobileLink = "+MobileLink+", Temperature = "+Temperature+", IsDayTime = "+IsDayTime+", LocalObservationDateTime = "+LocalObservationDateTime+"]";
    }
}
