package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class LadingPaymentInfo {

    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("CODAmount")
    Integer codAmount;

    @SerializedName("FeeCOD")
    Integer feeCod;

    @SerializedName("VATFeeCOD")
    String vatFeeCOD;

    @SerializedName("FeeType")
    String feeType;

    public String getVatFeeCOD() {
        return vatFeeCOD;
    }

    public void setVatFeeCOD(String vatFeeCOD) {
        this.vatFeeCOD = vatFeeCOD;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public Integer getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(Integer codAmount) {
        this.codAmount = codAmount;
    }

    public Integer getFeeCod() {
        return feeCod;
    }

    public void setFeeCod(Integer feeCod) {
        this.feeCod = feeCod;
    }


}
