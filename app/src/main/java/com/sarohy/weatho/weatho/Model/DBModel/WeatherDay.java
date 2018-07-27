package com.sarohy.weatho.weatho.Model.DBModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.sarohy.weatho.weatho.Utils;

import org.jetbrains.annotations.Nullable;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "WeatherDay",foreignKeys = @ForeignKey(entity = Location.class,
        parentColumns = "Key",
        childColumns = "CityKey", onDelete = CASCADE))
public class WeatherDay {
    @PrimaryKey(autoGenerate = true)
    private int key;

    @ColumnInfo(name = "CityKey")
    private String CityKey;

    @ColumnInfo(name = "Date")
    private Date Date;

    @ColumnInfo(name = "TemperatureMim")
    private String TemperatureMin;


    @ColumnInfo(name = "TemperatureMax")
    private String TemperatureMax;


    @ColumnInfo(name = "TemperatureUnit")
    private String TemperatureUnit;

    @ColumnInfo(name = "IconPhraseDay")
    private String IconPhraseDay;

    @ColumnInfo(name = "IconPhraseNight")
    private String IconPhraseNight;

    @ColumnInfo(name = "IconDay")
    private int IconDay;

    @ColumnInfo(name = "IconNight")
    private int IconNight;

    @ColumnInfo(name = "Link")
    private String Link;

    public WeatherDay(int key, String cityKey, java.util.Date date, String temperatureMin, String temperatureMax, String temperatureUnit, String iconPhraseDay,
                      String iconPhraseNight, int iconDay, int iconNight, String link) {
        this.key = key;
        CityKey = cityKey;
        Date = date;
        TemperatureMin = temperatureMin;
        TemperatureMax = temperatureMax;
        TemperatureUnit = temperatureUnit;
        IconPhraseDay = iconPhraseDay;
        IconPhraseNight = iconPhraseNight;
        IconDay = iconDay;
        IconNight = iconNight;
        Link = link;
    }

    public WeatherDay() {

    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getTemperatureMin() {
        return TemperatureMin;
    }

    public void setTemperatureMin(String temperatureMin) {
        TemperatureMin = temperatureMin;
    }

    public String getTemperatureMax() {
        return TemperatureMax;
    }

    public void setTemperatureMax(String temperatureMax) {
        TemperatureMax = temperatureMax;
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

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getTemperatureUnit() {
        return TemperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        TemperatureUnit = temperatureUnit;
    }

    public String getIconPhraseDay() {
        return IconPhraseDay;
    }

    public void setIconPhraseDay(String iconPhraseDay) {
        IconPhraseDay = iconPhraseDay;
    }

    public String getIconPhraseNight() {
        return IconPhraseNight;
    }

    public void setIconPhraseNight(String iconPhraseNight) {
        IconPhraseNight = iconPhraseNight;
    }

    public int getIconDay() {
        return IconDay;
    }

    public void setIconDay(int iconDay) {
        IconDay = iconDay;
    }

    public int getIconNight() {
        return IconNight;
    }

    public void setIconNight(int iconNight) {
        IconNight = iconNight;
    }

    @Nullable
    public CharSequence getHiLowTemperature(int temperatureUnit) {
        if (temperatureUnit==1) {
            if (TemperatureUnit.equals("F"))
                return Utils.UnitCovertFToC(TemperatureMax)+" / " + Utils.UnitCovertFToC(TemperatureMin);
            else
                return Utils.displayValue(TemperatureMax)+" / " + Utils.displayValue(TemperatureMin);
        }
        else {
            if (TemperatureUnit.equals("C"))
                return Utils.UnitCovertCToF(TemperatureMax)+" / " + Utils.UnitCovertCToF(TemperatureMin);
            else
                return Utils.displayValue(TemperatureMax)+" / " + Utils.displayValue(TemperatureMin);
        }
    }
}
