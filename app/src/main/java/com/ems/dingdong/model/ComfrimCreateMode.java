package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComfrimCreateMode {
    @SerializedName("PostmanId")
    private String PostmanId;

    @SerializedName("PostmanCode")
    private String PostmanCode;

    @SerializedName("POCode")
    private String POCode ;

    @SerializedName("RouteCode")
    private String RouteCode ;

    @SerializedName("ListLadingCode")
    private List<String> ListLadingCode;

    public String getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(String postmanId) {
        PostmanId = postmanId;
    }

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getRouteCode() {
        return RouteCode;
    }

    public void setRouteCode(String routeCode) {
        RouteCode = routeCode;
    }

    public List<String> getListLadingCode() {
        return ListLadingCode;
    }

    public void setListLadingCode(List<String> listLadingCode) {
        ListLadingCode = listLadingCode;
    }
}
