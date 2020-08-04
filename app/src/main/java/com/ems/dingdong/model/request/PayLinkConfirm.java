package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class PayLinkConfirm {
    @SerializedName("RequestId")
    String requestId;
    @SerializedName("OTPCode")
    String OTPCode;
    @SerializedName("Signature")
    String signature;
    @SerializedName("PostmanTel")
    String postmanTel;
    @SerializedName("PostmanCode")
    String postmanCode;
    @SerializedName("POCode")
    String pOCode;

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setOTPCode(String OTPCode) {
        this.OTPCode = OTPCode;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getOTPCode() {
        return OTPCode;
    }

    public String getPostmanTel() {
        return postmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        this.postmanTel = postmanTel;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public String getpOCode() {
        return pOCode;
    }

    public void setpOCode(String pOCode) {
        this.pOCode = pOCode;
    }
}
