package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class PayLinkRequest {

    @SerializedName("PostmanTel")
    String postmanTel;

    @SerializedName("PostmanCode")
    String postmanCode;

    @SerializedName("AccountType")
    Integer AccountType;

    @SerializedName("POCode")
    String pOCode;

    @SerializedName("Signature")
    String signature;

    public Integer getAccountType() {
        return AccountType;
    }

    public void setAccountType(Integer accountType) {
        AccountType = accountType;
    }

    public void setPostmanTel(String postmanTel) {
        this.postmanTel = postmanTel;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public void setpOCode(String pOCode) {
        this.pOCode = pOCode;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPostmanTel() {
        return postmanTel;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public String getpOCode() {
        return pOCode;
    }
}
