package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class DeliveryCheckAmountPaymentResponse {

    @SerializedName("LadingCode")
    private String ladingCode;
    @SerializedName("PayPostAmount")
    private Integer payPostAmount;
    @SerializedName("PNSAmount")
    private Integer PNSAmount;

    public String getLadingCode() {
        return ladingCode;
    }

    public Integer getPayPostAmount() {
        return payPostAmount;
    }

    public Integer getPNSAmount() {
        return PNSAmount;
    }
}
