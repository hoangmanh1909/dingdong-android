package com.ems.dingdong.model.thauchi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmartBankRequestCancelLinkRequest {
    /// <summary>
    /// Mã ngân hàng
    /// </summary>
    @SerializedName("BankCode")
    @Expose
    public String BankCode;
    /// <summary>
    /// Mã bc của bưu tá
    /// </summary>
    @SerializedName("POCode")
    @Expose
    public String POCode;
    /// <summary>
    /// Mã bưu tá
    /// </summary>
    @SerializedName("PostmanCode")
    @Expose
    public String PostmanCode;
    /// <summary>
    /// Loại GTTT
    /// 1 - CMND
    /// 2 - TCC
    /// 3 - PP
    /// </summary>
    @SerializedName("PIDType")
    @Expose
    public String PIDType;
    /// <summary>
    /// Số GTTT
    /// </summary>
    @SerializedName("PIDNumber")
    @Expose
    public String PIDNumber;
    /// <summary>
    /// Số tài khoản thấu chi SeABank
    /// </summary>
    @SerializedName("SeABankAccount")
    @Expose
    public String SeABankAccount;
    @SerializedName("SeABankAccountLimit")
    @Expose
    public String SeABankAccountLimit;

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

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

    public String getSeABankAccount() {
        return SeABankAccount;
    }

    public void setSeABankAccount(String seABankAccount) {
        SeABankAccount = seABankAccount;
    }

    public String getSeABankAccountLimit() {
        return SeABankAccountLimit;
    }

    public void setSeABankAccountLimit(String seABankAccountLimit) {
        SeABankAccountLimit = seABankAccountLimit;
    }
}
