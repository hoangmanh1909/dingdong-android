package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DecodeDiaChiResult extends SimpleResult {

    @SerializedName("Value")
    private ResultModelV1 object;

    public ResultModelV1 getObject() {
        return object;
    }

    public void setObject(ResultModelV1 object) {
        this.object = object;
    }
}
