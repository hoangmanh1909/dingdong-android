package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class BaseRequestModel {
    @SerializedName("POProvinceCode")
    private String POProvinceCode;

    @SerializedName("PODistrictCode")
    private String PODistrictCode;

    @SerializedName("POCode")
    private String POCode;

    @SerializedName("PostmanCode")
    private String PostmanCode;

    @SerializedName("PostmanId")
    private String PostmanId;

    @SerializedName("RouteCode")
    private String RouteCode;

    @SerializedName("RouteId")
    private long RouteId;

    public String getPOProvinceCode() {
        return POProvinceCode;
    }

    public void setPOProvinceCode(String POProvinceCode) {
        this.POProvinceCode = POProvinceCode;
    }

    public String getPODistrictCode() {
        return PODistrictCode;
    }

    public void setPODistrictCode(String PODistrictCode) {
        this.PODistrictCode = PODistrictCode;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(String postmanId) {
        PostmanId = postmanId;
    }

    public String getRouteCode() {
        return RouteCode;
    }

    public void setRouteCode(String routeCode) {
        RouteCode = routeCode;
    }

    public long getRouteId() {
        return RouteId;
    }

    public void setRouteId(long routeId) {
        RouteId = routeId;
    }
}
