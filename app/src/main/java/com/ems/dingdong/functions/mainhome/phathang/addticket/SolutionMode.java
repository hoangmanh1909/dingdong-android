package com.ems.dingdong.functions.mainhome.phathang.addticket;

import com.google.gson.annotations.SerializedName;

public class SolutionMode {
    @SerializedName("Name")
    String name;
    @SerializedName("Code")
    String Code;
    boolean is;

    public boolean isIs() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }

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
