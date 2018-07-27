package com.sarohy.weatho.weatho.Model.APIModel;

import com.google.gson.annotations.SerializedName;

public class Day {
    @SerializedName("IconPhrase")
    private String IconPhrase;

    @SerializedName("Icon")
    private String Icon;

    public String getIconPhrase ()
    {
        return IconPhrase;
    }

    public void setIconPhrase (String IconPhrase)
    {
        this.IconPhrase = IconPhrase;
    }

    public String getIcon ()
    {
        return Icon;
    }

    public void setIcon (String Icon)
    {
        this.Icon = Icon;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [IconPhrase = "+IconPhrase+", Icon = "+Icon+"]";
    }
}
