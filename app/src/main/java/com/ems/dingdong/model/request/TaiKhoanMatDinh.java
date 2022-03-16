package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class TaiKhoanMatDinh {
    @SerializedName("BankCode")
    String BankCode;

    @SerializedName("PostmanCode")
    String PostmanCode;

    @SerializedName("AccountNumber")
    String AccountNumber;

    @SerializedName("POCode")
    String POCode;

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

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
}
