package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderCreateBD13Mode {
    @SerializedName("TransportType")
    private String TransportType;
    @SerializedName("StartPoint")
    private Point StartPoint;
    @SerializedName("Data")
    private List<VietMapOrderCreateBD13DataRequest> Data;

    public String getTransportType() {
        return TransportType;
    }

    public void setTransportType(String transportType) {
        TransportType = transportType;
    }

    public Point getStartPoint() {
        return StartPoint;
    }

    public void setStartPoint(Point startPoint) {
        StartPoint = startPoint;
    }

    public List<VietMapOrderCreateBD13DataRequest> getData() {
        return Data;
    }

    public void setData(List<VietMapOrderCreateBD13DataRequest> data) {
        Data = data;
    }
}
