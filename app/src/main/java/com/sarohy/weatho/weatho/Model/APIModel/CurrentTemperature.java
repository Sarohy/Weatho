package com.sarohy.weatho.weatho.Model.APIModel;

import com.google.gson.annotations.SerializedName;

public class CurrentTemperature {
    @SerializedName("Metric")
    private Metric Metric;


    public Metric getMetric ()
    {
        return Metric;
    }

    public void setMetric (Metric Metric)
    {
        this.Metric = Metric;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [Metric = "+Metric+"]";
    }
}
