package com.ems.dingdong.model;

import com.ems.dingdong.model.request.vietmap.LocationMap;
import com.google.gson.annotations.SerializedName;

public class ResultModel {

    @SerializedName("smartCode")
    String smartCode;

    @SerializedName("location")
    LocationMap location;

    public LocationMap getLocation() {
        return location;
    }

    public void setLocation(LocationMap location) {
        this.location = location;
    }


    public String getSmartCode() {
        return smartCode;
    }

    public void setSmartCode(String smartCode) {
        this.smartCode = smartCode;
    }
}
