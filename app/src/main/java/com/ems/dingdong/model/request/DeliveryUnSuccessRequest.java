package com.ems.dingdong.model.request;

import com.ems.dingdong.model.DLVDeliveryUnSuccessRefundRequest;
import com.google.gson.annotations.SerializedName;

public class DeliveryUnSuccessRequest {

    @SerializedName("Data")
    private PushToPnsRequest Data;
    @SerializedName("PaymentBankCode")
    private String PaymentBankCode;
    @SerializedName("RefundInfo")
    private DLVDeliveryUnSuccessRefundRequest RefundInfo;

    public PushToPnsRequest getData() {
        return Data;
    }

    public DLVDeliveryUnSuccessRefundRequest getRefundInfo() {
        return RefundInfo;
    }

    public void setRefundInfo(DLVDeliveryUnSuccessRefundRequest refundInfo) {
        RefundInfo = refundInfo;
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
