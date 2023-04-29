package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class LadingRefundTotalRequest {
    @SerializedName("DeliveryFromDate")
    int DeliveryFromDate;
    @SerializedName("DeliveryToDate")
    int DeliveryToDate;
    @SerializedName("PostmanId")
    long PostmanId;
    @SerializedName("IsRefund")
    String IsRefund;

    public String getIsRefund() {
        return IsRefund;
    }

    public void setIsRefund(String isRefund) {
        IsRefund = isRefund;
    }

    public int getDeliveryFromDate() {
        return DeliveryFromDate;
    }

    public void setDeliveryFromDate(int deliveryFromDate) {
        DeliveryFromDate = deliveryFromDate;
    }

    public int getDeliveryToDate() {
        return DeliveryToDate;
    }

    public void setDeliveryToDate(int deliveryToDate) {
        DeliveryToDate = deliveryToDate;
    }

    public long getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(long postmanId) {
        PostmanId = postmanId;
    }
}
