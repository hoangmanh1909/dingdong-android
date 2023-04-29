package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class VMOrderBd13DataRequest {
    @SerializedName("Id")
    public long Id;
    @SerializedName("LadingCode")
    public String LadingCode;
    @SerializedName("ReceiverAddress")
    public String ReceiverAddress;
    @SerializedName("ReceiverLat")
    public String ReceiverLat;
    @SerializedName("ReceiverLon")
    public String ReceiverLon;
    @SerializedName("OrderNumber")
    public int OrderNumber;
    @SerializedName("ReceiverVpostCode ")
    public String ReceiverVpostCode;

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

    public String getReceiverLat() {
        return ReceiverLat;
    }

    public void setReceiverLat(String receiverLat) {
        ReceiverLat = receiverLat;
    }

    public String getReceiverLon() {
        return ReceiverLon;
    }

    public void setReceiverLon(String receiverLon) {
        ReceiverLon = receiverLon;
    }

    public int getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getReceiverVpostCode() {
        return ReceiverVpostCode;
    }

    public void setReceiverVpostCode(String receiverVpostCode) {
        ReceiverVpostCode = receiverVpostCode;
    }
}
