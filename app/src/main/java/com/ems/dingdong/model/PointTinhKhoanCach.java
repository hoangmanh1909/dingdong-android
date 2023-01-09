package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class PointTinhKhoanCach {

    @SerializedName("Longitude")
    double longitude;
    @SerializedName("Latitude")
    double latitude;


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
