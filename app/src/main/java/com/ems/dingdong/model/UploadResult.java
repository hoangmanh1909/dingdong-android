package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadResult extends SimpleResult {
    @SerializedName("ListValue")
    private List<FileInfo> fileInfos;

    public List<FileInfo> getFileInfos() {
        return fileInfos;
    }
}
