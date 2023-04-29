package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class SaveIDVmapModel {
    @SerializedName("PostmanCode")
    private String PostmanCode;
    @SerializedName("VMapId")
    private String VMapId;

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getVMapId() {
        return VMapId;
    }

    public void setVMapId(String VMapId) {
        this.VMapId = VMapId;
    }
}
