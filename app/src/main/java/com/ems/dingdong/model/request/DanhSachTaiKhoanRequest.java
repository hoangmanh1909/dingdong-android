package com.ems.dingdong.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DanhSachTaiKhoanRequest {
    @SerializedName("PIDType")
    @Expose
    private String PIDType;
    @SerializedName("BankCode")
    @Expose
    private String BankCode;
    @SerializedName("PIDNumber")
    @Expose
    private String PIDNumber;

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
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
}
