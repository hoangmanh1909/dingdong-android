package com.ems.dingdong.model;

import com.ems.dingdong.model.request.LadingCancelPaymentInfo;
import com.ems.dingdong.model.request.LadingPaymentInfo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EWalletRemoveRequest {
    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("RetRefNumber")
    String retRefNumber;

    @SerializedName("RemoveBy")
    String removeBy;

    @SerializedName("POCode")
    String POCode;

    @SerializedName("PostmanTel")
    String postmanTel;

    @SerializedName("Amount")
    long amount;

    @SerializedName("Ladings")
    List<LadingCancelPaymentInfo> ladingPaymentInfoList;

    public List<LadingCancelPaymentInfo> getLadingPaymentInfoList() {
        return ladingPaymentInfoList;
    }

    public void setLadingPaymentInfoList(List<LadingCancelPaymentInfo> ladingPaymentInfoList) {
        this.ladingPaymentInfoList = ladingPaymentInfoList;
    }

    public String getPostmanTel() {
        return postmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        this.postmanTel = postmanTel;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }


    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public String getRetRefNumber() {
        return retRefNumber;
    }

    public void setRetRefNumber(String retRefNumber) {
        this.retRefNumber = retRefNumber;
    }

    public String getRemoveBy() {
        return removeBy;
    }

    public void setRemoveBy(String removeBy) {
        this.removeBy = removeBy;
    }
}
