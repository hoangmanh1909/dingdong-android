package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PrepaidHistoryResult extends SimpleResult {
    @SerializedName("ListValue")
    private List<PrepaidHistoryResponse> prepaidResult;

    public List<PrepaidHistoryResponse> getRouteLadingInfo() {
        return prepaidResult;
    }
}
