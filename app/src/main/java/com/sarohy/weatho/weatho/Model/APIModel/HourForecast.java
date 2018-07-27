package com.sarohy.weatho.weatho.Model.APIModel;

import com.google.gson.annotations.SerializedName;

public class HourForecast {

    @SerializedName("IconPhrase")
    private String IconPhrase;

    @SerializedName("IsDaylight")
    private String IsDaylight;

    @SerializedName("EpochDateTime")
    private String EpochDateTime;

    @SerializedName("WeatherIcon")
    private String WeatherIcon;

    @SerializedName("DateTime")
    private String DateTime;

    @SerializedName("MobileLink")
    private String MobileLink;

    @SerializedName("PrecipitationProbability")
    private String PrecipitationProbability;

    @SerializedName("Temperature")
    private HourForecastTemperature Temperature;

    public String getIconPhrase ()
    {
        return IconPhrase;
    }

    public void setIconPhrase (String IconPhrase)
    {
        this.IconPhrase = IconPhrase;
    }

    public String getIsDaylight ()
    {
        return IsDaylight;
    }

    public void setIsDaylight (String IsDaylight)
    {
        this.IsDaylight = IsDaylight;
    }

    public String getEpochDateTime ()
    {
        return EpochDateTime;
    }

    public void setEpochDateTime (String EpochDateTime)
    {
        this.EpochDateTime = EpochDateTime;
    }

    public String getWeatherIcon ()
    {
        return WeatherIcon;
    }

    public void setWeatherIcon (String WeatherIcon)
    {
        this.WeatherIcon = WeatherIcon;
    }

    public String getDateTime ()
    {
        return DateTime;
    }

    public void setDateTime (String DateTime)
    {
        this.DateTime = DateTime;
    }

    public String getMobileLink ()
    {
        return MobileLink;
    }

    public void setMobileLink (String MobileLink)
    {
        this.MobileLink = MobileLink;
    }

    public String getPrecipitationProbability ()
    {
        return PrecipitationProbability;
    }

    public void setPrecipitationProbability (String PrecipitationProbability)
    {
        this.PrecipitationProbability = PrecipitationProbability;
    }

    public HourForecastTemperature getTemperature ()
    {
        return Temperature;
    }

    public void setTemperature (HourForecastTemperature Temperature)
    {
        this.Temperature = Temperature;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [IconPhrase = "+IconPhrase+", IsDaylight = "+IsDaylight+
                ", EpochDateTime = "+EpochDateTime+", WeatherIcon = "+WeatherIcon+
                ", DateTime = "+DateTime+", MobileLink = "+MobileLink+
                ", PrecipitationProbability = "+PrecipitationProbability+
                ", Temperature = "+Temperature+"]";
    }
}
