package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class LinkEWalletResponse {
    @SerializedName("request_id")
    String requestId;

    public String getRequestId() {
        return requestId;
    }
}
