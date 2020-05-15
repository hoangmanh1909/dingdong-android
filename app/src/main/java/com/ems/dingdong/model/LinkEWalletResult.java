package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class LinkEWalletResult extends SimpleResult {

    @SerializedName("Value")
    String response;

    public String getRequestId() {
        return response;
    }
}
