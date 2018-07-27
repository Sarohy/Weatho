package com.sarohy.weatho.weatho.Model.APIModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class City implements Serializable {
    @SerializedName("Key")
    private String Key;

    @SerializedName("Rank")
    private String Rank;

    @SerializedName("Type")
    private String Type;

    @SerializedName("AdministrativeArea")
    private AdministrativeArea AdministrativeArea;

    @SerializedName("LocalizedName")
    private String LocalizedName;

    @SerializedName("Country")
    private Country Country;

    @SerializedName("Version")
    private String Version;

    public String getKey ()
    {
        return Key;
    }

    public void setKey (String Key)
    {
        this.Key = Key;
    }

    public String getRank ()
    {
        return Rank;
    }

    public void setRank (String Rank)
    {
        this.Rank = Rank;
    }

    public String getType ()
    {
        return Type;
    }

    public void setType (String Type)
    {
        this.Type = Type;
    }

    public AdministrativeArea getAdministrativeArea ()
    {
        return AdministrativeArea;
    }

    public void setAdministrativeArea (AdministrativeArea AdministrativeArea)
    {
        this.AdministrativeArea = AdministrativeArea;
    }

    public String getLocalizedName ()
    {
        return LocalizedName;
    }

    public void setLocalizedName (String LocalizedName)
    {
        this.LocalizedName = LocalizedName;
    }

    public Country getCountry ()
    {
        return Country;
    }

    public void setCountry (Country Country)
    {
        this.Country = Country;
    }

    public String getVersion ()
    {
        return Version;
    }

    public void setVersion (String Version)
    {
        this.Version = Version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Key = "+Key+", Rank = "+Rank+", Type = "+Type+", AdministrativeArea = "+AdministrativeArea+", LocalizedName = "+LocalizedName+", Country = "+Country+", Version = "+Version+"]";
    }

    public String getDataToDisplay() {
        return LocalizedName + " "+ AdministrativeArea.getLocalizedName()+ " "+ Country.getLocalizedName();
    }
}
