package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("UnitCode")
    private String unitCode;
    @SerializedName("ID")
    private String iD;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("EmpGroupID")
    private String empGroupID;
    @SerializedName("UnitLink")
    private String unitLink;
    @SerializedName("MobileNumber")
    private String mobileNumber;
    @SerializedName("AmountMax")
    private String amountMax;
    @SerializedName("Balance")
    private String balance;
    @SerializedName("IsEms")
    private String isEms;




    public String getUnitCode() {
        return unitCode;
    }

    public String getiD() {
        return iD;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmpGroupID() {
        return empGroupID;
    }

    public String getUnitLink() {
        return unitLink;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAmountMax() {
        return amountMax;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIsEms() {
        return isEms;
    }
}
