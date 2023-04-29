package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata;

import com.ems.dingdong.model.VM_POSTMAN_ROUTE;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderCreateBD13Mode {
    @SerializedName("TransportType")
    private String TransportType;
    @SerializedName("DataType")
    private String DataType;
    @SerializedName("StartPoint")
    private Point StartPoint;
    @SerializedName("Data")
    private List<VietMapOrderCreateBD13DataRequest> Data;

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

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
