package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class PrepaidHistoryResponse {
    @SerializedName("Amount")
    private String amount;
    @SerializedName("MoblieNumber")
    private String moblieNumber;
    @SerializedName("Detail")
    private String Detail;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMoblieNumber() {
        return moblieNumber;
    }

    public void setMoblieNumber(String moblieNumber) {
        this.moblieNumber = moblieNumber;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getRetRefNum() {
        return RetRefNum;
    }

    public void setRetRefNum(String retRefNum) {
        RetRefNum = retRefNum;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getTransDetail() {
        return TransDetail;
    }

    public void setTransDetail(String transDetail) {
        TransDetail = transDetail;
    }

    @SerializedName("RetRefNum")
    private String RetRefNum;
    @SerializedName("TransDate")
    private String TransDate;
    @SerializedName("FullName")
    private String FullName;
    @SerializedName("TransDetail")
    private String TransDetail;
}
