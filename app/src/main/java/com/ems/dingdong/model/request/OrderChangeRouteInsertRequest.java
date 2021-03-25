package com.ems.dingdong.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderChangeRouteInsertRequest {
    @SerializedName("PostmanId")
    @Expose
    private int postmanId;

    @SerializedName("OrderCodes")
    @Expose
    private List<String> orderCodes;
    @SerializedName("POCode")
    @Expose
    private String pOCode;
    @SerializedName("RouteCode")
    @Expose
    private String routeCode;
    @SerializedName("PostmanCode")
    @Expose
    private String postmanCode;
    @SerializedName("ToRouteCode")
    @Expose
    private String toRouteCode;
    @SerializedName("ToPostmanCode")
    @Expose
    private String toPostmanCode;

    public int getPostmanId() {
        return postmanId;
    }

    public void setPostmanId(int postmanId) {
        this.postmanId = postmanId;
    }

    public List<String> getOrderCodes() {
        return orderCodes;
    }

    public void setOrderCodes(List<String> orderCodes) {
        this.orderCodes = orderCodes;
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

    public String getToRouteCode() {
        return toRouteCode;
    }

    public void setToRouteCode(String toRouteCode) {
        this.toRouteCode = toRouteCode;
    }

    public String getToPostmanCode() {
        return toPostmanCode;
    }

    public void setToPostmanCode(String toPostmanCode) {
        this.toPostmanCode = toPostmanCode;
    }
}
