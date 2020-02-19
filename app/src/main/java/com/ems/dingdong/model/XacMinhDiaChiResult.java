package com.ems.dingdong.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class XacMinhDiaChiResult extends SimpleResult {

    @SerializedName("Value")
    private Object object;

    public Object getResponseLocation() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
