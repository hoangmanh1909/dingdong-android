package com.ems.dingdong.functions.mainhome.main.data;

import com.google.gson.annotations.SerializedName;

public class CallLogMode {
    @SerializedName("callDate")
    private String callDate;
    @SerializedName("callDuration")
    private String callDuration;
    @SerializedName("date")
    private String date;
    @SerializedName("callType")
    private int callType;
    @SerializedName("phNumber")
    private String phNumber;
    @SerializedName("fromNumber")
    private String fromNumber;
    @SerializedName("PostmanCode")
    private String PostmanCode;

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }
}
