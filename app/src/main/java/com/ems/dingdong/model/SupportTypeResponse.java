package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class SupportTypeResponse {

    @SerializedName("ID")
    Integer id;

    @SerializedName("Name")
    String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
