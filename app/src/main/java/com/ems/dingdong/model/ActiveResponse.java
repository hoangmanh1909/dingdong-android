package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ActiveResponse {
    @SerializedName("account")
    Account account;

    @SerializedName("token")
    String token;

    public Account getAccount() {
        return account;
    }

    public String getToken() {
        return token;
    }
}
