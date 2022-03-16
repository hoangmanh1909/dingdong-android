package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentRequestModel {
    @SerializedName("PostmanCode")
    String postmanCode;

    @SerializedName("POCode")
    String poCode;

    @SerializedName("RouteCode")
    String routeCode;

    @SerializedName("PaymentToken")
    String paymentToken;

    @SerializedName("Ladings")
    List<LadingPaymentInfo> ladingPaymentInfoList;

    @SerializedName("Signature")
    String signature;

    @SerializedName("ServiceCode")
    String ServiceCode;
    @SerializedName("BankCode")
    String BankCode;
    @SerializedName("PostmanTel")
    String PostmanTel;

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getPostmanTel() {
        return PostmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        PostmanTel = postmanTel;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public void setLadingPaymentInfoList(List<LadingPaymentInfo> ladingPaymentInfoList) {
        this.ladingPaymentInfoList = ladingPaymentInfoList;
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

    public String getPaymentToken() {
        return paymentToken;
    }

    public List<LadingPaymentInfo> getLadingPaymentInfoList() {
        return ladingPaymentInfoList;
    }

    public String getSignature() {
        return signature;
    }
}
