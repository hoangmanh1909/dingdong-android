package com.ems.dingdong.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class UploadSingleResult extends SimpleResult {
    @SerializedName("Value")
    private String file;

    public String getFile() {
        return file;
    }
}
