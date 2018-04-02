package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("UnitCode")
    private String unitCode;

    public String getUnitCode() {
        return unitCode;
    }
}
