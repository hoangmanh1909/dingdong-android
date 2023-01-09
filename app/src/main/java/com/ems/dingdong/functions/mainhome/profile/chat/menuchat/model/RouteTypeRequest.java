package com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model;

import com.google.gson.annotations.SerializedName;

public class RouteTypeRequest {
    @SerializedName("POCode")
    private String POCode;
    @SerializedName("RouteType")
    private String RouteType;

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getRouteType() {
        return RouteType;
    }

    public void setRouteType(String routeType) {
        RouteType = routeType;
    }
}
