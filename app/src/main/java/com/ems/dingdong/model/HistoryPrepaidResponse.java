package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryPrepaidResponse {
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("LadingCode")
    @Expose
    private String ladingCode;

    public HistoryPrepaidResponse(Integer amount, String ladingCode) {
        this.amount = amount;
        this.ladingCode = ladingCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getLadingCode() {
        return ladingCode;
    }
}
