package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class SenderVpostcodeMode {
    @SerializedName("Id")
    int Id;
    @SerializedName("SenderVpostcode")
    String SenderVpostcode;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSenderVpostcode() {
        return SenderVpostcode;
    }

    public void setSenderVpostcode(String senderVpostcode) {
        SenderVpostcode = senderVpostcode;
    }
}
