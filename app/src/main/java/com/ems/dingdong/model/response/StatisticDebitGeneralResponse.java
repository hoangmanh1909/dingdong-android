package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticDebitGeneralResponse {

    @SerializedName("DebitSuccessQuantity")
    private String successQuantity;
    @SerializedName("DebitSuccessAmount")
    private String successAmount;
    @SerializedName("DebitErrorQuantity")
    private String errorQuantity;
    @SerializedName("DebitErrorAmount")
    private String errorAmount;

    public String getSuccessQuantity() {
        return successQuantity;
    }

    public String getSuccessAmount() {
        return successAmount;
    }

    public String getErrorQuantity() {
        return errorQuantity;
    }

    public String getErrorAmount() {
        return errorAmount;
    }
}
