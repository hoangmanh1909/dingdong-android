package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class SearchMode {
    @SerializedName("POCode")
    String POCode ;
    @SerializedName("LadingCode")
    String ladingCode;

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }
}
