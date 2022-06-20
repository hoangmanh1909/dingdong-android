package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class VpostcodeModel {

    int id;
    int value;
    String smartCode;

    public String getSmartCode() {
        return smartCode;
    }

    public void setSmartCode(String smartCode) {
        this.smartCode = smartCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @SerializedName("FullAddress")
    String fullAdress;
    @SerializedName("ReceiverVpostcode")
    String ReceiverVpostcode;
    @SerializedName("SenderVpostcode")
    String SenderVpostcode;
    @SerializedName("MaE")
    String MaE;
    @SerializedName("Vitri")
    String Vitri;
    @SerializedName("latitude")
    double latitude;
    @SerializedName("longitude")
    double longitude;

    public String getVitri() {
        return Vitri;
    }

    public void setVitri(String vitri) {
        Vitri = vitri;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFullAdress() {
        return fullAdress;
    }

    public void setFullAdress(String fullAdress) {
        this.fullAdress = fullAdress;
    }

    public String getReceiverVpostcode() {
        return ReceiverVpostcode;
    }

    public void setReceiverVpostcode(String receiverVpostcode) {
        ReceiverVpostcode = receiverVpostcode;
    }

    public String getSenderVpostcode() {
        return SenderVpostcode;
    }

    public void setSenderVpostcode(String senderVpostcode) {
        SenderVpostcode = senderVpostcode;
    }

    public String getMaE() {
        return MaE;
    }

    public void setMaE(String maE) {
        MaE = maE;
    }
}
