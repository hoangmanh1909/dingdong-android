package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class StatisticDeliveryGeneralRequest {
    @SerializedName("PostmanId")
    private String postmanId;
    @SerializedName("FromDate")
    private String fromDate;
    @SerializedName("ToDate")
    private String toDate;

    public StatisticDeliveryGeneralRequest(String postmanId, String fromDate, String toDate) {
        this.postmanId = postmanId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
}
