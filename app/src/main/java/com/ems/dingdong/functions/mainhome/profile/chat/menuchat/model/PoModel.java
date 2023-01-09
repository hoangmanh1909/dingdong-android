package com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model;

import com.google.gson.annotations.SerializedName;

public class PoModel {
    @SerializedName("POId")
    private String POId;
    @SerializedName("POCode")
    private String POCode;
    @SerializedName("POName")
    private String POName;
    @SerializedName("UnitCode")
    private String UnitCode;

    public String getPOId() {
        return POId;
    }

    public void setPOId(String POId) {
        this.POId = POId;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getPOName() {
        return POName;
    }

    public void setPOName(String POName) {
        this.POName = POName;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        UnitCode = unitCode;
    }
}
