package com.ems.dingdong.model;

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
    @SerializedName("Description")
    private String description;
    @SerializedName("ActionTypeName")
    private String actionTypeName;

    public String getPOCode() {
        return POCode;
    }

    public String getPOName() {
        return POName;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public String getDescription() {
        return description;
    }

    public String getActionTypeName() {
        return actionTypeName;
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

    public void setStatusDate(String statusDate) {
        StatusDate = statusDate;
    }

    public void setStatusTime(String statusTime) {
        StatusTime = statusTime;
    }
}
