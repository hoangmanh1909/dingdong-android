package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class SupportRequest {
    @SerializedName("SuportType")
    Integer supportType;

    @SerializedName("PostmanID")
    String postmanId;

    @SerializedName("Description")
    String description;

    @SerializedName("LadingCode")
    String ladingCode;

    public void setSupportType(Integer supportType) {
        this.supportType = supportType;
    }

    public void setPostmanId(String postmanId) {
        this.postmanId = postmanId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }
}
