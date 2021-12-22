package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class RouteInfo {

    @SerializedName("RouteCode")
    private String RouteCode;
    @SerializedName("RouteName")
    private String RouteName;
    @SerializedName("RouteId")
    private String RouteId;
    @SerializedName("RouteType")
    private String RouteType;

    @SerializedName("TransportType")
    private int TransportType;

    public int getTransportType() {
        return TransportType;
    }

    public void setTransportType(int transportType) {
        TransportType = transportType;
    }

    public String getRouteType() {
        return RouteType;
    }

    public void setRouteType(String routeType) {
        RouteType = routeType;
    }

    public String getRouteCode() {
        return RouteCode;
    }

    public void setRouteCode(String routeCode) {
        RouteCode = routeCode;
    }


    public String getRouteName() {
        return RouteName;
    }

    public void setRouteName(String routeName) {
        RouteName = routeName;
    }


    public String getRouteId() {
        return RouteId;
    }

    public void setRouteId(String routeId) {
        RouteId = routeId;
    }
}
