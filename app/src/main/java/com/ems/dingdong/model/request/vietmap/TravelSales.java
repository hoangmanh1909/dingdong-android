package com.ems.dingdong.model.request.vietmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TravelSales {

    @SerializedName("TransportType")
    @Expose
    private int TransportType;

    @SerializedName("points")
    @Expose
    private List<RouteRequest> points;

    public int getTransportType() {
        return TransportType;
    }

    public void setTransportType(int transportType) {
        TransportType = transportType;
    }

    public List<RouteRequest> getPoints() {
        return points;
    }

    public void setPoints(List<RouteRequest> points) {
        this.points = points;
    }
}
