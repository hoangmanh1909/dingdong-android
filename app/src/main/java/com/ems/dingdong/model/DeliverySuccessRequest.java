package com.ems.dingdong.model;

import com.ems.dingdong.model.request.PaypostPaymentRequest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliverySuccessRequest {

    @SerializedName("DeliveryData")
    @Expose
    private List<PaypostPaymentRequest> paypostPaymentRequests;

    @SerializedName("PaymentBankCode")
    @Expose
    private String paymentBankCode ;


    public List<PaypostPaymentRequest> getPaypostPaymentRequests() {
        return paypostPaymentRequests;
    }

    public void setPaypostPaymentRequests(List<PaypostPaymentRequest> paypostPaymentRequests) {
        this.paypostPaymentRequests = paypostPaymentRequests;
    }

    public String getPaymentBankCode() {
        return paymentBankCode;
    }

    public void setPaymentBankCode(String paymentBankCode) {
        this.paymentBankCode = paymentBankCode;
    }
}
