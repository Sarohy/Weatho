package com.sarohy.weatho.weatho.Model.DBModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "Location")
public class Location implements Serializable {
    @PrimaryKey
    @NonNull
    private String Key;

    @ColumnInfo(name = "AdministrativeArea")
    private String AdministrativeArea;

    @ColumnInfo(name = "Name")
    private String LocalizedName;

    @ColumnInfo(name = "Country")
    private String Country;

    @ColumnInfo(name = "CountryCode")
    private String CountryCode;


    @NonNull
    public String getKey ()
    {
        return Key;
    }

    public void setKey (@NonNull String Key)
    {
        this.Key = Key;
    }

    public Location(@NonNull String key, String administrativeArea, String localizedName, String country, String countryCode) {
        Key = key;
        AdministrativeArea = administrativeArea;
        LocalizedName = localizedName;
        Country = country;
        CountryCode = countryCode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Key = "+Key+", AdministrativeArea = "+AdministrativeArea+", LocalizedName = "+LocalizedName+", Country = "+Country+"]";
    }

    public String getDataToDisplay() {
        return LocalizedName + " "+ AdministrativeArea+ " "+ Country;
    }


    public String getAdministrativeArea() {
        return AdministrativeArea;
    }

    public String getLocalizedName() {
        return LocalizedName;
    }

    public String getCountry() {
        return Country;
    }

    public void setAdministrativeArea(String administrativeArea) {
        AdministrativeArea = administrativeArea;
    }

    public void setLocalizedName(String localizedName) {
        LocalizedName = localizedName;
    }

    public void setCountry(String country) {
        Country = country;
    }


    public Location() {
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }
}
