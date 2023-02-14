package com.ems.dingdong.functions.mainhome.phathang.addticket;

import com.google.gson.annotations.SerializedName;

public class SolutionMode {
    @SerializedName("Name")
    String name;
    @SerializedName("Code")
    String Code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
