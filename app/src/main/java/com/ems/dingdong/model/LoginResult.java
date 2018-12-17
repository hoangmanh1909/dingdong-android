package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class LoginResult extends SimpleResult {
    @SerializedName("Value")
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
