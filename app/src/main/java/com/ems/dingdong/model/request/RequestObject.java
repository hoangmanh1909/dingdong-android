package com.ems.dingdong.model.request;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class RequestObject {
    @SerializedName("Channel")
    private String channel;
    @SerializedName("CommandCode")
    private String code;
    @SerializedName("Data")
    private String data;
    @SerializedName("Time")
    private String time;
    @SerializedName("Info")
    private String info;
    @SerializedName("Signature")
    private String signature;

    public RequestObject(String channel, String version, String code, String data, String time,String info , String signature) {
        if (!TextUtils.isEmpty(channel))
            this.channel = channel;
        else
            this.channel = "ANDROID";
        this.code = code;
        this.time = time;
        this.data = data;
        this.signature = signature;
        this.info=info;
    }
}