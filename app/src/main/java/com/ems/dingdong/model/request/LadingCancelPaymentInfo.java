package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class LadingCancelPaymentInfo {

    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("Amount")
    Integer amount ;

    @SerializedName("RetRefNumber")
    String retRefNumber;

    public String getRetRefNumber() {
        return retRefNumber;
    }

    public void setRetRefNumber(String retRefNumber) {
        this.retRefNumber = retRefNumber;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
