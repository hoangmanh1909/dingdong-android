package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ResultModel {


    @SerializedName("smartCode")
    String smartCode;

    public String getSmartCode() {
        return smartCode;
    }

    public void setSmartCode(String smartCode) {
        this.smartCode = smartCode;
    }
}
