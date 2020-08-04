package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class CancelDeliveryStatisticRequest {

    @SerializedName("POCode")
    String pOCode;

    @SerializedName("PostmanCode")
    String PostmanCode;

    @SerializedName("RouteCode")
    String RouteCode;

    @SerializedName("FromDate")
    Integer fromDate;

    @SerializedName("ToDate")
    Integer  toDate;

    @SerializedName("StatusCode")
    String statusCode;

    public void setpOCode(String pOCode) {
        this.pOCode = pOCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public void setRouteCode(String routeCode) {
        RouteCode = routeCode;
    }

    public void setFromDate(Integer fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(Integer toDate) {
        this.toDate = toDate;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
