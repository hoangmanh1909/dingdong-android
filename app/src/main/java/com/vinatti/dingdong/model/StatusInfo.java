package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class StatusInfo {
    @SerializedName("POCode")
    String POCode;
    @SerializedName("POName")
    String POName;
    @SerializedName("StatusMessage")
    String StatusMessage;
    @SerializedName("StatusDate")
    String StatusDate;
    @SerializedName("StatusTime")
    String StatusTime;

    public String getPOCode() {
        return POCode;
    }

    public String getPOName() {
        return POName;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public String getStatusDate() {
        return StatusDate;
    }

    public String getStatusTime() {
        return StatusTime;
    }
}
