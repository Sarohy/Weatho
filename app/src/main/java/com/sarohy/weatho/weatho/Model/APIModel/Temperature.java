package com.sarohy.weatho.weatho.Model.APIModel;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("Maximum")
    private Maximum Maximum;

    @SerializedName("Minimum")
    private Minimum Minimum;

    public Maximum getMaximum ()
    {
        return Maximum;
    }

    public void setMaximum (Maximum Maximum)
    {
        this.Maximum = Maximum;
    }

    public Minimum getMinimum ()
    {
        return Minimum;
    }

    public void setMinimum (Minimum Minimum)
    {
        this.Minimum = Minimum;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Maximum = "+Maximum+", Minimum = "+Minimum+"]";
    }
}
