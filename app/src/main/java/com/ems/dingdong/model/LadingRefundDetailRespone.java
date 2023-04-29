package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class LadingRefundDetailRespone {
    @SerializedName("LadingCode")
    String LadingCode;
    @SerializedName("Id")
    int Id;
    @SerializedName("ReceiverAddress")
    String ReceiverAddress;
    @SerializedName("DeliveryDate")
    String DeliveryDate;
    @SerializedName("TrangThai")
    String TrangThai;

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }
}
