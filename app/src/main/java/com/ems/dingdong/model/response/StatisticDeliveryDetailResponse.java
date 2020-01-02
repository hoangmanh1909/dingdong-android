package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticDeliveryDetailResponse {
    @SerializedName("LadingCode")
    private String ladingCode;
    @SerializedName("Amount")
    private String amount;

    public String getLadingCode() {
        return ladingCode;
    }

    public String getAmount() {
        return amount;
    }
}
