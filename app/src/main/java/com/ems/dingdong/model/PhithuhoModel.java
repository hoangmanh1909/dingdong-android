package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class PhithuhoModel {
    @SerializedName("TrackingCode")
    private String trackingCode;
    @SerializedName("FeeShip")
    private String feeShip;

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(String feeShip) {
        this.feeShip = feeShip;
    }
}
