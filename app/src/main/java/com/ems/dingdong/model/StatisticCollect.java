package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatisticCollect {
    @SerializedName("StatusCode")
    private String statusCode;
    @SerializedName("StatusName")
    private String statusName;
    @SerializedName("Count")
    private String count;
    @SerializedName("Details")
    private List<StatisticDetailCollect> details;

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getCount() {
        return count;
    }

    public List<StatisticDetailCollect> getDetails() {
        return details;
    }
}
