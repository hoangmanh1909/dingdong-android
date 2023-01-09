package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class SMLRequest {
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("POCode")
    private String POCode;
    @SerializedName("ReceiverMobileNumber")
    private String ReceiverMobileNumber;
    @SerializedName("ReceiverEmail")
    private String ReceiverEmail;
    @SerializedName("HubCode")
    private String HubCode;
    @SerializedName("PostmanId")
    private String PostmanId;
    @SerializedName("FeeCOD")
    private long feeCOD;
    @SerializedName("FeeShip")
    private long feeShip;
    @SerializedName("FeePPA")
    private long feePPA;
    @SerializedName("FeeCollectLater")
    private long feeCollectLater;
    @SerializedName("AmountCOD")
    private long amountCOD;

    public long getFeeCOD() {
        return feeCOD;
    }

    public void setFeeCOD(long feeCOD) {
        this.feeCOD = feeCOD;
    }


    public long getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(long feeShip) {
        this.feeShip = feeShip;
    }

    public long getFeePPA() {
        return feePPA;
    }

    public void setFeePPA(long feePPA) {
        this.feePPA = feePPA;
    }

    public long getFeeCollectLater() {
        return feeCollectLater;
    }

    public void setFeeCollectLater(long feeCollectLater) {
        this.feeCollectLater = feeCollectLater;
    }

    public long getAmountCOD() {
        return amountCOD;
    }

    public void setAmountCOD(long amountCOD) {
        this.amountCOD = amountCOD;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getReceiverMobileNumber() {
        return ReceiverMobileNumber;
    }

    public void setReceiverMobileNumber(String receiverMobileNumber) {
        ReceiverMobileNumber = receiverMobileNumber;
    }

    public String getReceiverEmail() {
        return ReceiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        ReceiverEmail = receiverEmail;
    }

    public String getHubCode() {
        return HubCode;
    }

    public void setHubCode(String hubCode) {
        HubCode = hubCode;
    }

    public String getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(String postmanId) {
        PostmanId = postmanId;
    }
}
