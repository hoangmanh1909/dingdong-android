package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class EWalletRequestResponse {
    @SerializedName("TransId")
    String tranid;

    @SerializedName("RetRefNumber")
    String retRefNumber;

    public String getTranid() {
        return tranid;
    }

    public String getRetRefNumber() {
        return retRefNumber;
    }
}
