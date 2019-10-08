package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ConfirmAllOrderPostman {
    @SerializedName("SuccessRecord")
    private String successRecord;
    @SerializedName("ErrorRecord")
    private String errorRecord;

    public String getSuccessRecord() {
        return successRecord;
    }

    public String getErrorRecord() {
        return errorRecord;
    }
}
