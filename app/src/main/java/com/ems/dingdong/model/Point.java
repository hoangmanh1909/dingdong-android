package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class Point {

    @SerializedName("Id")
    String id;
    @SerializedName("Longitude")
    String longitude;
    @SerializedName("Latitude")
    String latitude;

    public String getId() {
        return id;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }
}
