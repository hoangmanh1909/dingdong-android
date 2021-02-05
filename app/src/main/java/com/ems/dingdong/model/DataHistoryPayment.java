package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataHistoryPayment {
    @SerializedName("FromDate")
    @Expose
    private String fromDate;
    @SerializedName("ToDate")
    @Expose
    private String toDate;
    @SerializedName("POCode")
    @Expose
    private String pOCode;
    @SerializedName("RouteCode")
    @Expose
    private String routeCode;
    @SerializedName("PostmanCode")
    @Expose
    private String postmanCode;
    @SerializedName("Status")
    @Expose
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//    public DataHistoryPayment(String fromDate, String toDate, String pOCode, String routeCode, String postmanCode) {
//        this.fromDate = fromDate;
//        this.toDate = toDate;
//        this.pOCode = pOCode;
//        this.routeCode = routeCode;
//        this.postmanCode = postmanCode;
//    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getPOCode() {
        return pOCode;
    }

    public void setPOCode(String pOCode) {
        this.pOCode = pOCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

}
