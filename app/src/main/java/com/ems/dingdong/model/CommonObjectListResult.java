package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommonObjectListResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<CommonObject> list;

    public ArrayList<CommonObject> getList() {
        return list;
    }
}
