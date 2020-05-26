package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class LadingPaymentInfo {

    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("CODAmount")
    String codAmount;

    @SerializedName("FeeCOD")
    String feeCod;

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public String getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(String codAmount) {
        this.codAmount = codAmount;
    }

    public String getFeeCod() {
        return feeCod;
    }

    public void setFeeCod(String feeCod) {
        this.feeCod = feeCod;
    }
}
