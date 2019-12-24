package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatisticDetailCollect {
    @SerializedName("OrderCode")
    private String orderCode;
    @SerializedName("Ladings")
    private List<StatisticOrderDetailCollect> ladings;

    public String getOrderCode() {
        return orderCode;
    }

    public List<StatisticOrderDetailCollect> getLadings() {
        return ladings;
    }
}
