package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class LadingCancelPaymentInfo {

    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("Amount")
    Integer amount ;

    @SerializedName("RetRefNumber")
    String retRefNumber;

    @SerializedName("Fee")
    int fee;

    @SerializedName("FeeType")
    int FeeType;
    @SerializedName("EWalletTransId")
    String EWalletTransId;

    public String getEWalletTransId() {
        return EWalletTransId;
    }

    public void setEWalletTransId(String EWalletTransId) {
        this.EWalletTransId = EWalletTransId;
    }
    public int getFeeType() {
        return FeeType;
    }

    public void setFeeType(int feeType) {
        FeeType = feeType;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

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
