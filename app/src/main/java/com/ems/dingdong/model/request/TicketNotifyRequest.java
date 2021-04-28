package com.ems.dingdong.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketNotifyRequest {
    @SerializedName("FromDate")
    @Expose
    private int fromDate;
    @SerializedName("ToDate")
    @Expose
    private int toDate;

    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;


    public int getFromDate() {
        return fromDate;
    }

    public void setFromDate(int fromDate) {
        this.fromDate = fromDate;
    }

    public int getToDate() {
        return toDate;
    }

    public void setToDate(int toDate) {
        this.toDate = toDate;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
