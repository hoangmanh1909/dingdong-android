package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticPaymentResponse {
    @SerializedName("CollectAmount")
    private String collectAmount;
    @SerializedName("PaymentAmount")
    private String paymentAmount;
    @SerializedName("DebitAmount")
    private String debitAmount;

    public String getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(String collectAmount) {
        this.collectAmount = collectAmount;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }
}
