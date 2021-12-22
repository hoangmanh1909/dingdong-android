package com.ems.dingdong.model.request.vietmap;

import com.google.gson.annotations.SerializedName;

public class LocationMap {

    @SerializedName("lat")
    double latitude;
    @SerializedName("lng")
    double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
