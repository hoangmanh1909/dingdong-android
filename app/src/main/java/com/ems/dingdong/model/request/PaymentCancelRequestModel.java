package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentCancelRequestModel {
    @SerializedName("PostmanTel")
    String postmanTel;

    @SerializedName("POCode")
    String poCode;

    @SerializedName("CancelBy")
    String cancelBy;

    @SerializedName("ReasonType")
    int reasonType;

    @SerializedName("ReasonText")
    String reasonText;

    @SerializedName("Ladings")
    List<LadingCancelPaymentInfo> ladingPaymentInfoList;

    @SerializedName("Signature")
    String signature;

    public int getReasonType() {
        return reasonType;
    }

    public void setReasonType(int reasonType) {
        this.reasonType = reasonType;
    }

    public String getReasonText() {
        return reasonText;
    }

    public void setReasonText(String reasonText) {
        this.reasonText = reasonText;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanTel = postmanCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }


    public void setLadingPaymentInfoList(List<LadingCancelPaymentInfo> ladingPaymentInfoList) {
        this.ladingPaymentInfoList = ladingPaymentInfoList;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(String cancelBy) {
        this.cancelBy = cancelBy;
    }

    public String getPostmanCode() {
        return postmanTel;
    }

    public String getPoCode() {
        return poCode;
    }


    public List<LadingCancelPaymentInfo> getLadingPaymentInfoList() {
        return ladingPaymentInfoList;
    }

    public String getSignature() {
        return signature;
    }
}
