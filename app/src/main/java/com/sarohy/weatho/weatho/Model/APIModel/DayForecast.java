package com.sarohy.weatho.weatho.Model.APIModel;

import com.google.gson.annotations.SerializedName;

public class DayForecast {
    @SerializedName("Date")
    private String Date;

    @SerializedName("MobileLink")
    private String MobileLink;

    @SerializedName("Night")
    private Night Night;

    @SerializedName("Temperature")
    private Temperature Temperature;

    @SerializedName("EpochDate")
    private String EpochDate;

    @SerializedName("Day")
    private Day Day;


    public String getDate ()
    {
        return Date;
    }

    public void setDate (String Date)
    {
        this.Date = Date;
    }

    public String getMobileLink ()
    {
        return MobileLink;
    }

    public void setMobileLink (String MobileLink)
    {
        this.MobileLink = MobileLink;
    }

    public Night getNight ()
    {
        return Night;
    }

    public void setNight (Night Night)
    {
        this.Night = Night;
    }

    public Temperature getTemperature ()
    {
        return Temperature;
    }

    public void setTemperature (Temperature Temperature)
    {
        this.Temperature = Temperature;
    }

    public String getEpochDate ()
    {
        return EpochDate;
    }

    public void setEpochDate (String EpochDate)
    {
        this.EpochDate = EpochDate;
    }

    public Day getDay ()
    {
        return Day;
    }

    public void setDay (Day Day)
    {
        this.Day = Day;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ Date = "+Date+", MobileLink = "+MobileLink+", Night = "+Night+", Temperature = "+Temperature+", EpochDate = "+EpochDate+", Day = "+Day+"]";
    }
}
