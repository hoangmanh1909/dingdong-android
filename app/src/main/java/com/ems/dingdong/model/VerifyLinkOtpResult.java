package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class VerifyLinkOtpResult extends SimpleResult {

    @SerializedName("Value")
    String token;

    public String getToken() {
        return token;
    }
}
