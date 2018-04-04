package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class XacNhanTinResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<XacNhanTin> list;

    public ArrayList<XacNhanTin> getList() {
        return list;
    }
}
