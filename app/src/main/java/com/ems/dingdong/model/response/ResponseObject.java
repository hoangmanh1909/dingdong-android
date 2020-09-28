package com.ems.dingdong.model.response;

import com.ems.dingdong.model.SimpleResult;
import com.google.gson.annotations.SerializedName;

public class ResponseObject extends SimpleResult {
    @SerializedName("Data")
    private String Data;

    public String getData() {
        return Data;
    }
}
