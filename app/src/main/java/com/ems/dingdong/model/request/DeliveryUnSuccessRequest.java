package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class DeliveryUnSuccessRequest {

    @SerializedName("Data")
    private PushToPnsRequest Data;
    @SerializedName("PaymentBankCode")
    private String PaymentBankCode;

    public PushToPnsRequest getData() {
        return Data;
    }

    public void setData(PushToPnsRequest data) {
        Data = data;
    }

    public String getPaymentBankCode() {
        return PaymentBankCode;
    }

    public void setPaymentBankCode(String paymentBankCode) {
        PaymentBankCode = paymentBankCode;
    }
}
