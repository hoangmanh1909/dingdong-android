package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class VpostcodeModel {

    int id;
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

    @SerializedName("FullAddress")
    String fullAdress;
    @SerializedName("ReceiverVpostcode")
    String ReceiverVpostcode;
    @SerializedName("SenderVpostcode")
    String SenderVpostcode;
    @SerializedName("MaE")
    String MaE;

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
