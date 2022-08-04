package com.ems.dingdong.functions.mainhome.main.data;

import com.google.gson.annotations.SerializedName;

public class MainMode {
    @SerializedName("PostmanCode")
    private String PostmanCode;
    @SerializedName("PostmanTel")
    private String PostmanTel;

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getPostmanTel() {
        return PostmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        PostmanTel = postmanTel;
    }
}
