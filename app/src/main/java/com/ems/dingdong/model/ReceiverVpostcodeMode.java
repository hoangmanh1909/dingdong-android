package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ReceiverVpostcodeMode {
    @SerializedName("Id")
    int Id;
    @SerializedName("ReceiverVpostcode")
    String ReceiverVpostcode;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getReceiverVpostcode() {
        return ReceiverVpostcode;
    }

    public void setReceiverVpostcode(String receiverVpostcode) {
        ReceiverVpostcode = receiverVpostcode;
    }
}
