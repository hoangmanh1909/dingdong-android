package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class DanhSachTaiKhoanRespone {
    @SerializedName("AccountNumber")
    private String AccountNumber;
    @SerializedName("AccountLimitExpired")
    private String AccountLimitExpired;

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
