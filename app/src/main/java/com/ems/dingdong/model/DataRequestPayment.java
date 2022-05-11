package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRequestPayment {
//    @SerializedName("Code")
//    @Expose
//    private String code;

    @SerializedName("Data")
    @Expose
    private String data;

    @SerializedName("Signature")
    String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
