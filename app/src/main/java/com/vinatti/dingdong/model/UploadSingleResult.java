package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadSingleResult extends SimpleResult {
    @SerializedName("Value")
    private String file;

    public String getFile() {
        return file;
    }
}
