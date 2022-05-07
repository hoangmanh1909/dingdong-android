package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ThuGomRespone extends SimpleResult{
    @SerializedName("Value")
    public ThuGomResponeValue Value;

    public ThuGomResponeValue getValue() {
        return Value;
    }

    public void setValue(ThuGomResponeValue value) {
        Value = value;
    }
}
