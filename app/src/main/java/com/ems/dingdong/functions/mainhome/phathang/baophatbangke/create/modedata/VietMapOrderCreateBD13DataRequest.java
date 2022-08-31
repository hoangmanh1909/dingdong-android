package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata;

import com.google.gson.annotations.SerializedName;

public class VietMapOrderCreateBD13DataRequest {

    @SerializedName("Id")
    private long Id;
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("ReceiverAddress")
    private String ReceiverAddress;
    @SerializedName("OrderNumber")
    private String OrderNumber;
    @SerializedName("ReceiverLat")
    private double ReceiverLat;
    @SerializedName("ReceiverLon")
    private double ReceiverLon;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public double getReceiverLat() {
        return ReceiverLat;
    }

    public void setReceiverLat(double receiverLat) {
        ReceiverLat = receiverLat;
    }

    public double getReceiverLon() {
        return ReceiverLon;
    }

    public void setReceiverLon(double receiverLon) {
        ReceiverLon = receiverLon;
    }
}
