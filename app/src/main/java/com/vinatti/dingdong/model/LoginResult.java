package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class LoginResult extends SimpleResult {
    @SerializedName("Value")
    private LoginResponse loginResponse;

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }
}
