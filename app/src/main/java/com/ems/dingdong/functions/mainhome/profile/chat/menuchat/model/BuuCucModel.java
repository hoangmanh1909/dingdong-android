package com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model;

import com.google.gson.annotations.SerializedName;

public class BuuCucModel {
    @SerializedName("UnitCode")
    private String UnitCode;
    @SerializedName("UnitName")
    private String UnitName;

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        UnitCode = unitCode;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }
}
