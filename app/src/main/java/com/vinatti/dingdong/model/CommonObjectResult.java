package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommonObjectResult extends SimpleResult {
    @SerializedName("Value")
    private CommonObject commonObject;

    public CommonObject getCommonObject() {
        return commonObject;
    }
}
