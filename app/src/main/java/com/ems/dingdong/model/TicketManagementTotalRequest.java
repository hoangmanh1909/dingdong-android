package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class TicketManagementTotalRequest {
    /// <summary>
    /// Từ ngày YYYYMMDD
    /// </summary>
    @SerializedName("FromDate")
    private int FromDate;
    @SerializedName("ToDate")
    private int ToDate;
    @SerializedName("PostmanCode")
    private String PostmanCode;

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

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }
}
