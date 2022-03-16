package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SMB002Mode {
    @SerializedName("POCode")
    @Expose
    private String POCode;
    @SerializedName("PostmanCode")
    @Expose
    private String PostmanCode;
    @SerializedName("PostmanTel")
    @Expose
    private String PostmanTel;
    @SerializedName("PIDType")
    @Expose
    private String PIDType;
    @SerializedName("PIDNumber")
    @Expose
    private String PIDNumber;
    @SerializedName("AccountNumber")
    @Expose
    private String AccountNumber;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("AccountName")
    @Expose
    private String AccountName;
    @SerializedName("AccountLimit")
    @Expose
    private String AccountLimit;
    @SerializedName("PIDDate")
    @Expose
    private String PIDDate;
    @SerializedName("PIDWhere")
    @Expose
    private String PIDWhere;
    @SerializedName("AccountLimitExpired")
    @Expose
    private String AccountLimitExpired;

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getPostmanTel() {
        return PostmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        PostmanTel = postmanTel;
    }

    public String getPIDType() {
        return PIDType;
    }

    public void setPIDType(String PIDType) {
        this.PIDType = PIDType;
    }

    public String getPIDNumber() {
        return PIDNumber;
    }

    public void setPIDNumber(String PIDNumber) {
        this.PIDNumber = PIDNumber;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getAccountLimit() {
        return AccountLimit;
    }

    public void setAccountLimit(String accountLimit) {
        AccountLimit = accountLimit;
    }

    public String getPIDDate() {
        return PIDDate;
    }

    public void setPIDDate(String PIDDate) {
        this.PIDDate = PIDDate;
    }

    public String getPIDWhere() {
        return PIDWhere;
    }

    public void setPIDWhere(String PIDWhere) {
        this.PIDWhere = PIDWhere;
    }

    public String getAccountLimitExpired() {
        return AccountLimitExpired;
    }

    public void setAccountLimitExpired(String accountLimitExpired) {
        AccountLimitExpired = accountLimitExpired;
    }
}
