package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.create.modedata;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.annotations.Ignore;

public class VietMapOrderCreateBD13DataRequest {

    @SerializedName("Id")
    private long Id;
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("ReceiverAddress")
    private String ReceiverAddress;
    @SerializedName("ReceiverVpostCode")
    private String ReceiverVpostCode;
    @SerializedName("OrderNumber")
    private String OrderNumber;
    @SerializedName("ReceiverLat")
    private String ReceiverLat;
    @SerializedName("ReceiverLon")
    private String ReceiverLon;
    @SerializedName("DataType")
    private String DataType;
    @SerializedName("SenderVpostcode")
    private String SenderVpostcode;
    @SerializedName("SenderLat")
    private String SenderLat;
    @SerializedName("SenderLon")
    private String SenderLon;
    List<String> codeS;
    List<String> codeS1;

    public String getSenderVpostcode() {
        return SenderVpostcode;
    }

    public void setSenderVpostcode(String senderVpostcode) {
        SenderVpostcode = senderVpostcode;
    }

    public String getSenderLat() {
        return SenderLat;
    }

    public void setSenderLat(String senderLat) {
        SenderLat = senderLat;
    }

    public String getSenderLon() {
        return SenderLon;
    }

    public void setSenderLon(String senderLon) {
        SenderLon = senderLon;
    }

    public List<String> getCodeS() {
        return codeS;
    }

    public void setCodeS(List<String> codeS) {
        this.codeS = codeS;
    }

    public List<String> getCodeS1() {
        return codeS1;
    }

    public void setCodeS1(List<String> codeS1) {
        this.codeS1 = codeS1;
    }

    public String getReceiverVpostCode() {
        return ReceiverVpostCode;
    }

    public void setReceiverVpostCode(String receiverVpostCode) {
        ReceiverVpostCode = receiverVpostCode;
    }

    public String getDataType() {
        return DataType;
    }

    public void setDataType(String dataType) {
        DataType = dataType;
    }

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
}
