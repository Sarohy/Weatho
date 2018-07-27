package com.sarohy.weatho.weatho.Model.APIModel;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {

    @SerializedName("ID")
    private String ID;

    @ColumnInfo(name = "LocalizedName")
    private String LocalizedName;

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
    }

    public String getLocalizedName ()
    {
        return LocalizedName;
    }

    public void setLocalizedName (String LocalizedName)
    {
        this.LocalizedName = LocalizedName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ID = "+ID+", LocalizedName = "+LocalizedName+"]";
    }
}
