package com.vinatti.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bd13Code {
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Service")
    @Expose
    private String service;
    @SerializedName("CollectAmount")
    @Expose
    private String collectAmount;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("Address")
    @Expose
    private String address;


    public Bd13Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getService() {
        return service;
    }

    public String getCollectAmount() {
        return collectAmount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getAddress() {
        return address;
    }
}
