package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class VerifyLinkOtpResponse {
    @SerializedName("token")
    String token;

    public String getToken() {
        return token;
    }
}
