package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class MapResult extends SimpleResult {

    @SerializedName("Value")
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
