package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class StatusInfo extends RealmObject {
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
    @SerializedName("ToNumber")
    private String ToNumber;
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("StartTime")
    private String StartTime;
    @SerializedName("Status")
    private String Status;
    @SerializedName("CallTypeName")
    private String CallTypeName;
    @SerializedName("RecordFile")
    private String RecordFile;

    public String getToNumber() {
        return ToNumber;
    }

    public void setToNumber(String toNumber) {
        ToNumber = toNumber;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCallTypeName() {
        return CallTypeName;
    }

    public void setCallTypeName(String callTypeName) {
        CallTypeName = callTypeName;
    }

    public String getRecordFile() {
        return RecordFile;
    }

    public void setRecordFile(String recordFile) {
        RecordFile = recordFile;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public void setPOName(String POName) {
        this.POName = POName;
    }

    public void setStatusMessage(String statusMessage) {
        StatusMessage = statusMessage;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setActionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

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
