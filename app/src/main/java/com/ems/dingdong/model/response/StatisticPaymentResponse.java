package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticPaymentResponse {
    @SerializedName("CollectAmount")
    private long collectAmount;
    @SerializedName("PaymentAmount")
    private long paymentAmount;
    @SerializedName("DebitAmount")
    private long debitAmount;

    public long getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(long collectAmount) {
        this.collectAmount = collectAmount;
    }

    public long getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public long getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(long debitAmount) {
        this.debitAmount = debitAmount;
    }
}
