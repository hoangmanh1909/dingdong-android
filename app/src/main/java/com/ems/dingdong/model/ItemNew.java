package com.ems.dingdong.model;

public class ItemNew {
    String bankcode;
    String paymentToken;

    public ItemNew(String bankcode, String paymentToken) {
        this.bankcode = bankcode;
        this.paymentToken = paymentToken;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }
}
