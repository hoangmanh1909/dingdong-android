package com.ems.dingdong.model;

import com.ems.dingdong.model.response.DeliveryCheckAmountPaymentResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryCheckAmountPaymentResult extends SimpleResult {

    @SerializedName("ListValue")
    private List<DeliveryCheckAmountPaymentResponse> paymentResponses;

    public List<DeliveryCheckAmountPaymentResponse> getPaymentResponses() {
        return paymentResponses;
    }
}
