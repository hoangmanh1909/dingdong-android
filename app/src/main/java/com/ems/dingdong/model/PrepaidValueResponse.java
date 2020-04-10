package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class PrepaidValueResponse {
    @SerializedName("Name")
    private String name;
    @SerializedName("MoblieNumber")
    private String MoblieNumber;
    @SerializedName("PIDNumber")
    private String PIDNumber;

    public String getName() {
        return name;
    }

    public String getMoblieNumber() {
        return MoblieNumber;
    }

    public String getPIDNumber() {
        return PIDNumber;
    }
}
