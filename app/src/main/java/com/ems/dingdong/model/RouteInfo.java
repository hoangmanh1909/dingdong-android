package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class RouteInfo {
    public String getRouteCode() {
        return RouteCode;
    }

    public void setRouteCode(String routeCode) {
        RouteCode = routeCode;
    }

    @SerializedName("RouteCode")
    private String RouteCode;

    public String getRouteName() {
        return RouteName;
    }

    public void setRouteName(String routeName) {
        RouteName = routeName;
    }

    @SerializedName("RouteName")
    private String RouteName;

    @SerializedName("RouteId")
    private  String RouteId;

    public String getRouteId() {
        return RouteId;
    }

    public void setRouteId(String routeId) {
        RouteId = routeId;
    }
}
