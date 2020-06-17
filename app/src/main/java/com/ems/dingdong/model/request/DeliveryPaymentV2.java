package com.ems.dingdong.model.request;

import com.ems.dingdong.model.response.DeliveryCheckAmountPaymentResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryPaymentV2 {
    @SerializedName("Request")
    private List<PaypostPaymentRequest> paymentRequests;
    @SerializedName("IsAutoUpdateCODAmount")
    private boolean isAutoUpdateCODAmount;
    @SerializedName("Values")
    private List<DeliveryCheckAmountPaymentResponse> paymentResponses;

    public void setPaymentRequests(List<PaypostPaymentRequest> paymentRequests) {
        this.paymentRequests = paymentRequests;
    }

    public void setAutoUpdateCODAmount(boolean autoUpdateCODAmount) {
        isAutoUpdateCODAmount = autoUpdateCODAmount;
    }

    public void setPaymentResponses(List<DeliveryCheckAmountPaymentResponse> paymentResponses) {
        this.paymentResponses = paymentResponses;
    }
}
