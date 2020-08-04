package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChangeRouteResult extends SimpleResult {
    @SerializedName("ListValue")
    private List<DetailLadingCode> routeLadingInfo;

    public List<DetailLadingCode> getRouteLadingInfo() {
        return routeLadingInfo;
    }
}
