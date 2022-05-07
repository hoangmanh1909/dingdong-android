package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class XacMinhRespone extends SimpleResult {
    @SerializedName("Value")
    public String Value;

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
