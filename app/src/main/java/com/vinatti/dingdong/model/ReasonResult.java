package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReasonResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<ReasonInfo> reasonInfos;

    public ArrayList<ReasonInfo> getReasonInfos() {
        return reasonInfos;
    }
}
