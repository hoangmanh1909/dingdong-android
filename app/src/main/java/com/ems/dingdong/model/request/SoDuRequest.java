package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class SoDuRequest {
    @SerializedName("BalanceAmount")
    String BalanceAmount;

    public String getBalanceAmount() {
        return BalanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        BalanceAmount = balanceAmount;
    }
}
