package com.ems.dingdong.model;

import com.ems.dingdong.model.response.RouteResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteResult extends SimpleResult {
    @SerializedName("ListValue")
    List<RouteResponse> routeResponses;

    public List<RouteResponse> getRouteResponses() {
        return routeResponses;
    }
}
