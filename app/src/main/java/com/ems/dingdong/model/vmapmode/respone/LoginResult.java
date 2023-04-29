package com.ems.dingdong.model.vmapmode.respone;

import com.google.gson.annotations.SerializedName;

public class LoginResult {
    @SerializedName("token")
    String token;
    @SerializedName("refreshToken")
    String refreshToken;
    @SerializedName("scope")
    String scope;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
