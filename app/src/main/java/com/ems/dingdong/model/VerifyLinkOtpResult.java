package com.ems.dingdong.model;

import com.ems.dingdong.model.response.VerifyLinkOtpResponse;
import com.google.gson.annotations.SerializedName;

public class VerifyLinkOtpResult extends SimplePaypostResult {

    @SerializedName("data")
    VerifyLinkOtpResponse response;

    public VerifyLinkOtpResponse getResponse() {
        return response;
    }
}
