package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RouteInfoResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<RouteInfo> reasonInfos;

    public ArrayList<RouteInfo> getRouteInfos() {
        return reasonInfos;
    }
}
