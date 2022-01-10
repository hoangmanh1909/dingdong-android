package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticDeliveryDetailResponse {
    @SerializedName("LadingCode")
    private String ladingCode;
    @SerializedName("Amount")
    private String amount;
    @SerializedName("FeePPA")
    private long feePPA;
    @SerializedName("FeePA")
    private long FeePA;
    @SerializedName("FeeShip")
    private long feeShip;
    @SerializedName("FeeCollectLater")
    private long feeCollectLater;
    @SerializedName("FeePPAPNS")
    private long feePPAPNS;
    @SerializedName("FeeShipPNS")
    private long feeShipPNS;
    @SerializedName("FeeCollectLaterPNS")
    private long feeCollectLaterPNS;
    @SerializedName("IsCancelOrder")
    private boolean isCancelOrder;
    @SerializedName("FeeCancelOrder")
    private long feeCancelOrder;

    @SerializedName("ReceiveCollectFee")
    private String receiveCollectFee;

    long tongtien ;

    public long getTongtien() {
        return tongtien;
    }

    public void setTongtien(long tongtien) {
        this.tongtien = tongtien;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public long getFeePPA() {
        return feePPA;
    }

    public void setFeePPA(long feePPA) {
        this.feePPA = feePPA;
    }

    public long getFeePA() {
        return FeePA;
    }

    public void setFeePA(long feePA) {
        FeePA = feePA;
    }

    public long getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(long feeShip) {
        this.feeShip = feeShip;
    }

    public long getFeeCollectLater() {
        return feeCollectLater;
    }

    public void setFeeCollectLater(long feeCollectLater) {
        this.feeCollectLater = feeCollectLater;
    }

    public long getFeePPAPNS() {
        return feePPAPNS;
    }

    public void setFeePPAPNS(long feePPAPNS) {
        this.feePPAPNS = feePPAPNS;
    }

    public long getFeeShipPNS() {
        return feeShipPNS;
    }

    public void setFeeShipPNS(long feeShipPNS) {
        this.feeShipPNS = feeShipPNS;
    }

    public long getFeeCollectLaterPNS() {
        return feeCollectLaterPNS;
    }

    public void setFeeCollectLaterPNS(long feeCollectLaterPNS) {
        this.feeCollectLaterPNS = feeCollectLaterPNS;
    }

    public boolean isCancelOrder() {
        return isCancelOrder;
    }

    public void setCancelOrder(boolean cancelOrder) {
        isCancelOrder = cancelOrder;
    }

    public long getFeeCancelOrder() {
        return feeCancelOrder;
    }

    public void setFeeCancelOrder(long feeCancelOrder) {
        this.feeCancelOrder = feeCancelOrder;
    }

    public String getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public void setReceiveCollectFee(String receiveCollectFee) {
        this.receiveCollectFee = receiveCollectFee;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
