package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticDebitDetailResponse {
    @SerializedName("LadingCode")
    private String ladingCode;
    @SerializedName("DebitAmount")
    private String amount;
    @SerializedName("FeeTypeName")
    private String FeeTypeName;

    public String getFeeTypeName() {
        return FeeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        FeeTypeName = feeTypeName;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
