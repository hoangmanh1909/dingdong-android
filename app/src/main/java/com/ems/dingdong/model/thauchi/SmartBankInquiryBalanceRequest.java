package com.ems.dingdong.model.thauchi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmartBankInquiryBalanceRequest {
    /// <summary>
    /// Mã ngân hàng
    /// </summary>
    @SerializedName("BankCode")
    @Expose
    public String BankCode;

    /// <summary>
    /// Mã bưu tá
    /// </summary>
    @SerializedName("PostmanCode")
    @Expose
    public String PostmanCode;
    /// <summary>
    /// Số tài khoản thấu chi SeABank
    /// </summary>
    @SerializedName("SeABankAccount")
    @Expose
    public String SeABankAccount;

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getSeABankAccount() {
        return SeABankAccount;
    }

    public void setSeABankAccount(String seABankAccount) {
        SeABankAccount = seABankAccount;
    }
}
