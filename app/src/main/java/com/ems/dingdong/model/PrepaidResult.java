package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrepaidResult extends SimpleResult {
    @SerializedName("ListValue")
    private List<CommonObject> prepaidResult;

    @SerializedName("Value")
    private PrepaidValueResponse value;

    public List<CommonObject> getRouteLadingInfo() {
        return prepaidResult;
    }

    public PrepaidValueResponse getValue() {
        return value;
    }
}
