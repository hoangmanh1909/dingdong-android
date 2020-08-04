package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentConfirmModel {

    @SerializedName("PostmanCode")
    String postmanCode;

    @SerializedName("POCode")
    String poCode;

    @SerializedName("RouteCode")
    String routeCode;

    @SerializedName("TransId")
    String transId;

    @SerializedName("OTPCode")
    String otpCode;

    @SerializedName("RetRefNumber")
    String retRefNumber;

    @SerializedName("PaymentToken")
    String paymentToken;

    @SerializedName("Signature")
    String signature;

    @SerializedName("PostmanTel")
    String postmanTel;

    @SerializedName("Ladings")
    List<LadingPaymentInfo> ladingPaymentInfoList;

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    public void setRetRefNumber(String retRefNumber) {
        this.retRefNumber = retRefNumber;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getTransId() {
        return transId;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public String getRetRefNumber() {
        return retRefNumber;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public String getSignature() {
        return signature;
    }

    public List<LadingPaymentInfo> getLadingPaymentInfoList() {
        return ladingPaymentInfoList;
    }

    public void setLadingPaymentInfoList(List<LadingPaymentInfo> ladingPaymentInfoList) {
        this.ladingPaymentInfoList = ladingPaymentInfoList;
    }

    public void setPostmanTel(String postmanTel) {
        this.postmanTel = postmanTel;
    }
}
