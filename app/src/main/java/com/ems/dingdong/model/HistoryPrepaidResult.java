package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryPrepaidResult extends SimpleResult {
    @SerializedName("ListValue")
    @Expose
    private List<HistoryPrepaidResponse> bd13Codes = null;

    public List<HistoryPrepaidResponse> getBd13Codes() {
        return bd13Codes;
    }
}
