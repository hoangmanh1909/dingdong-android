package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ActiveResult extends SimpleResult {
    @SerializedName("SignCode")
    private String signCode;

    public String getSignCode() {
        return signCode;
    }
}
