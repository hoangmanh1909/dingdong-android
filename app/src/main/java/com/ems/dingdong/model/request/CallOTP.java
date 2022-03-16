package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class CallOTP {
    @SerializedName("BankCode")
    String BankCode;
    @SerializedName("POCode")
    String POCode;
    @SerializedName("PostmanCode")
    String PostmanCode;
    @SerializedName("AccountNumber ")
    String AccountNumber;

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
}
