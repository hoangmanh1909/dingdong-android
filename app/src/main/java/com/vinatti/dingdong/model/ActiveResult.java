package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ActiveResult extends SimpleResult {
    @SerializedName("Value")
    private String signCode;

    public String getSignCode() {
        return signCode;
    }
}
