package com.vinatti.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryCallResult extends SimpleResult {
    @SerializedName("ListValue")
    @Expose
    private List<HistoryCallInfo> historyCallInfos = null;

    public List<HistoryCallInfo> getHistoryCallInfos() {
        return historyCallInfos;
    }
}
