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
