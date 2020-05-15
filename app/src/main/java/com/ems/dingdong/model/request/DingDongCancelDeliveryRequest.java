package com.ems.dingdong.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DingDongCancelDeliveryRequest {
    @SerializedName("AmndPOCode")
    @Expose
    private String amndPOCode;
    @SerializedName("AmndEmp")
    @Expose
    private Integer amndEmp;
    @SerializedName("LadingCode")
    @Expose
    private String ladingCode;
    @SerializedName("LadingJourneyId")
    @Expose
    private Integer ladingJourneyId;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("PaymentPayPostStatus")
    @Expose
    private String paymentPayPostStatus;
    @SerializedName("Signature")
    @Expose
    private String signature;
    @SerializedName("CancelDeliveryReasonType")
    @Expose
    private String cancelDeliveryReasonType;
    @SerializedName("BatchCode")
    @Expose
    private String batchCode;
    @SerializedName("IsPaymentBatch")
    @Expose
    private String isPaymentBatch;

    public String getAmndPOCode() {
        return amndPOCode;
    }

    public void setAmndPOCode(String amndPOCode) {
        this.amndPOCode = amndPOCode;
    }

    public Integer getAmndEmp() {
        return amndEmp;
    }

    public void setAmndEmp(Integer amndEmp) {
        this.amndEmp = amndEmp;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCancelDeliveryReasonType() {
        return cancelDeliveryReasonType;
    }

    public void setCancelDeliveryReasonType(String cancelDeliveryReasonType) {
        this.cancelDeliveryReasonType = cancelDeliveryReasonType;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getIsPaymentBatch() {
        return isPaymentBatch;
    }

    public void setIsPaymentBatch(String isPaymentBatch) {
        this.isPaymentBatch = isPaymentBatch;
    }
}
