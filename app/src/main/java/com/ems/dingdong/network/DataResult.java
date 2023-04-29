package com.ems.dingdong.network;


import com.google.gson.annotations.SerializedName;


public class DataResult {
    @SerializedName("Data")
    private String getData;

    public String getData() {
        return getData;
    }
}
