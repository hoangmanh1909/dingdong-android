package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class AccountCtel {
    @SerializedName("name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
