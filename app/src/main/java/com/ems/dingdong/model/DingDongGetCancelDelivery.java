package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DingDongGetCancelDelivery {
    @SerializedName("LadingCode")
    @Expose
    private String ladingCode;
    @SerializedName("LadingJourneyId")
    @Expose
    private Integer ladingJourneyId;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Fee")
    @Expose
    private Integer fee;
    @SerializedName("PaymentPayPostStatus")
    @Expose
    private String paymentPayPostStatus;
    @SerializedName("ReceiverAddress")
    @Expose
    private String receiverAddress;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("ReceiverTel")
    @Expose
    private String receiverTel;
    @SerializedName("SenderName")
    @Expose
    private String senderName;
    @SerializedName("SenderAddress")
    @Expose
    private String senderAddress;
    @SerializedName("BatchCode")
    @Expose
    private String batchCode;
    @SerializedName("IsPaymentBatch")
    @Expose
    private String isPaymentBatch;
    @SerializedName("PaymentLadingCode")
    private String paymentLadingCode;


    @SerializedName("FeeShip")
    @Expose
    private Integer FeeShip;
    @SerializedName("FeeCollectLater")
    @Expose
    private Integer FeeCollectLater;
    @SerializedName("FeePPA")
    @Expose
    private Integer FeePPA;
    @SerializedName("FeePA")
    @Expose
    private Integer FeePA;
    @SerializedName("FeeCOD")
    private long feeCOD;
    @SerializedName("FeeCancelOrder")
    private long FeeCancelOrder;
    @SerializedName("FeeC")
    private long feeC;

    public long getFeeC() {
        return feeC;
    }

    public void setFeeC(long feeC) {
        this.feeC = feeC;
    }

    public long getFeeCOD() {
        return feeCOD;
    }

    public void setFeeCOD(long feeCOD) {
        this.feeCOD = feeCOD;
    }

    public long getFeeCancelOrder() {
        return FeeCancelOrder;
    }

    public void setFeeCancelOrder(long feeCancelOrder) {
        FeeCancelOrder = feeCancelOrder;
    }

    public Integer getFeePA() {
        return FeePA;
    }

    public void setFeePA(Integer feePA) {
        FeePA = feePA;
    }

    public Integer getFeeShip() {
        return FeeShip;
    }

    public void setFeeShip(Integer feeShip) {
        FeeShip = feeShip;
    }

    public Integer getFeeCollectLater() {
        return FeeCollectLater;
    }

    public void setFeeCollectLater(Integer feeCollectLater) {
        FeeCollectLater = feeCollectLater;
    }

    public Integer getFeePPA() {
        return FeePPA;
    }

    public void setFeePPA(Integer feePPA) {
        FeePPA = feePPA;
    }

    private boolean selected;

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public Integer getLadingJourneyId() {
        return ladingJourneyId;
    }

    public void setLadingJourneyId(Integer ladingJourneyId) {
        this.ladingJourneyId = ladingJourneyId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPaymentPayPostStatus() {
        return paymentPayPostStatus;
    }

    public void setPaymentPayPostStatus(String paymentPayPostStatus) {
        this.paymentPayPostStatus = paymentPayPostStatus;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public String getIsPaymentBatch() {
        return isPaymentBatch;
    }

    public String getPaymentLadingCode() {
        return paymentLadingCode;
    }
}
