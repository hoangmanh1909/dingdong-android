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
    @SerializedName("PaymentPayPostStatus")
    @Expose
    private String paymentPayPostStatus;
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
}
