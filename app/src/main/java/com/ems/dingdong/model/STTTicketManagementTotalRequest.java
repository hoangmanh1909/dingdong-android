package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class STTTicketManagementTotalRequest {
    @SerializedName("PostmanId")
    private long PostmanId;
    @SerializedName("LadingCode")
    private String ladingCode;
    @SerializedName("StatusCode")
    private String StatusCode;
    @SerializedName("FromDate")
    private int FromDate;
    @SerializedName("ToDate")
    private int ToDate;

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public long getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(long postmanId) {
        PostmanId = postmanId;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public int getFromDate() {
        return FromDate;
    }

    public void setFromDate(int fromDate) {
        FromDate = fromDate;
    }

    public int getToDate() {
        return ToDate;
    }

    public void setToDate(int toDate) {
        ToDate = toDate;
    }
}
