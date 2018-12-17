package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class FileInfo {
    @SerializedName("FileName")
    private String nameFile;

    public String getNameFile() {
        return nameFile;
    }
}
