package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GachNoResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<GachNo> list;

    public ArrayList<GachNo> getList() {
        return list;
    }
}
