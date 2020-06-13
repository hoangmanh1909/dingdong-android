package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuportTypeResult extends SimpleResult{
    @SerializedName("ListValue")
    List<SupportTypeResponse> responseList;

    public List<SupportTypeResponse> getResponseList() {
        return responseList;
    }
}
