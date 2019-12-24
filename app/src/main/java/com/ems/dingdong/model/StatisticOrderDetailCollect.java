package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class StatisticOrderDetailCollect {
    @SerializedName("LadingCode")
    private String ladingCode;

    public String getLadingCode() {
        return ladingCode;
    }
}
