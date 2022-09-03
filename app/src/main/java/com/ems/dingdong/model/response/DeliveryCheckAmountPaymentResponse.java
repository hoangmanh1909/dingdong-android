package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class DeliveryCheckAmountPaymentResponse {

    @SerializedName("LadingCode")
    private String ladingCode;

    @SerializedName("PayPostAmount")
    private Integer payPostAmount;

    @SerializedName("PNSAmount")
    private Integer PNSAmount;

    @SerializedName("FeePPAPP")
    private Integer FeePPAPP;

    @SerializedName("FeePPAPNS")
    private Integer FeePPAPNS;

    @SerializedName("FeeShipPP")
    private Integer FeeShipPP;

    @SerializedName("FeeShipPNS")
    private Integer FeeShipPNS;

    @SerializedName("FeeCollectLaterPP")
    private Integer FeeCollectLaterPP;

    @SerializedName("FeeCollectLaterPNS")
    private Integer FeeCollectLaterPNS;

    @SerializedName("FeePA")
    private long FeePA;

    @SerializedName("FeePAPNS")
    private long FeePAPNS;

    public long getFeePA() {
        return FeePA;
    }

    public void setFeePA(long feePA) {
        FeePA = feePA;
    }

    public long getFeePAPNS() {
        return FeePAPNS;
    }

    public void setFeePAPNS(long feePAPNS) {
        FeePAPNS = feePAPNS;
    }

    public Integer getFeeCollectLaterPNS() {
        return FeeCollectLaterPNS;
    }

    public void setFeeCollectLaterPNS(Integer feeCollectLaterPNS) {
        FeeCollectLaterPNS = feeCollectLaterPNS;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public void setPayPostAmount(Integer payPostAmount) {
        this.payPostAmount = payPostAmount;
    }

    public void setPNSAmount(Integer PNSAmount) {
        this.PNSAmount = PNSAmount;
    }

    public Integer getFeePPAPP() {
        return FeePPAPP;
    }

    public void setFeePPAPP(Integer feePPAPP) {
        FeePPAPP = feePPAPP;
    }

    public Integer getFeePPAPNS() {
        return FeePPAPNS;
    }

    public void setFeePPAPNS(Integer feePPAPNS) {
        FeePPAPNS = feePPAPNS;
    }

    public Integer getFeeShipPP() {
        return FeeShipPP;
    }

    public void setFeeShipPP(Integer feeShipPP) {
        FeeShipPP = feeShipPP;
    }

    public Integer getFeeShipPNS() {
        return FeeShipPNS;
    }

    public void setFeeShipPNS(Integer feeShipPNS) {
        FeeShipPNS = feeShipPNS;
    }

    public Integer getFeeCollectLaterPP() {
        return FeeCollectLaterPP;
    }

    public void setFeeCollectLaterPP(Integer feeCollectLaterPP) {
        FeeCollectLaterPP = feeCollectLaterPP;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public Integer getPayPostAmount() {
        return payPostAmount;
    }

    public Integer getPNSAmount() {
        return PNSAmount;
    }
}
