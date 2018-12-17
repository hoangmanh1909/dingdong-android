package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class CommonObjectResult extends SimpleResult {
    @SerializedName("Value")
    private CommonObject commonObject;

    public CommonObject getCommonObject() {
        return commonObject;
    }
}
