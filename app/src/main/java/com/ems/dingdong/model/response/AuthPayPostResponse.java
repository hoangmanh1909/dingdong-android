package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class AuthPayPostResponse {
    @SerializedName("token")
    String token;

    @SerializedName("expired_in")
    Integer expiredIn;

    public String getToken() {
        return token;
    }

    public Integer getExpiredIn() {
        return expiredIn;
    }
}
