package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class UploadSingleResult extends SimpleResult {
    @SerializedName("Value")
    private String file;

    public String getFile() {
        return file;
    }
}
