package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class BalanceRespone {
    @SerializedName("Balance")
    private String Balance;

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}
