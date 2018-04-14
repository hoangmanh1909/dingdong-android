package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class XacNhanTinResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<CommonObject> list;

    public ArrayList<CommonObject> getList() {
        return list;
    }
}
