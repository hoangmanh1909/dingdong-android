package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class DanhSachTaiKhoanRespone {
    @SerializedName("AccountNumber")
    private String AccountNumber;
    @SerializedName("AccountName")
    private String AccountName;
    @SerializedName("AccountLimit")
    private String AccountLimit;
    @SerializedName("AccountLimitExpired")
    private String AccountLimitExpired;

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

    boolean ischeck = false;

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getAccountLimitExpired() {
        return AccountLimitExpired;
    }

    public void setAccountLimitExpired(String accountLimitExpired) {
        AccountLimitExpired = accountLimitExpired;
    }
}
