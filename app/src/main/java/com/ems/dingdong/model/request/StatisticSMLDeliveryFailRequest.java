package com.ems.dingdong.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticSMLDeliveryFailRequest {
    @SerializedName("Type")
    @Expose
    private int type;
    @SerializedName("FromDate")
    @Expose
    private int fromDate;
    @SerializedName("ToDate")
    @Expose
    private int toDate;
    @SerializedName("PostmanCode")
    @Expose
    private String postmanCode;
    @SerializedName("RouteCode")
    @Expose
    private String routeCode;
    @SerializedName("POCode")
    @Expose
    private String pOCode;
    @SerializedName("ServiceCode")
    @Expose
    private String serviceCode;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFromDate() {
        return fromDate;
    }

    public void setFromDate(int fromDate) {
        this.fromDate = fromDate;
    }

    public int getToDate() {
        return toDate;
    }

    public void setToDate(int toDate) {
        this.toDate = toDate;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getPOCode() {
        return pOCode;
    }

    public void setPOCode(String pOCode) {
        this.pOCode = pOCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
