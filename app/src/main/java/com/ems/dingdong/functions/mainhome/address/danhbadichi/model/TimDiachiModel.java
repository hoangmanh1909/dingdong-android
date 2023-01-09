package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimDiachiModel {
    @SerializedName("Text")
    @Expose
    private String Text;
    @SerializedName("Lat")
    @Expose
    private String Lat;
    @SerializedName("Lon")
    @Expose
    private String Lon;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }
}
