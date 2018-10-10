package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StatusInfo extends RealmObject{
    @PrimaryKey
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
    @SerializedName("StatusCode")
    String StatusCode;
    @SerializedName("ReasonCode")
    private String reasonCode;

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

    public String getStatusCode() {
        return StatusCode;
    }

    public String getReasonCode() {
        return reasonCode;
    }
}
