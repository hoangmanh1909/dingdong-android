package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ModeTu {
    @SerializedName("HubCode")
    String HubCode;

    @SerializedName("HubName")
    String HubName;

    @SerializedName("HubAddress")
    String HubAddress;

    public String getHubCode() {
        return HubCode;
    }

    public void setHubCode(String hubCode) {
        HubCode = hubCode;
    }

    public String getHubName() {
        return HubName;
    }

    public void setHubName(String hubName) {
        HubName = hubName;
    }

    public String getHubAddress() {
        return HubAddress;
    }

    public void setHubAddress(String hubAddress) {
        HubAddress = hubAddress;
    }
}
