package com.ems.dingdong.model;

import com.ems.dingdong.model.response.EWalletDataResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EWalletRemoveDataRequest {
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("EWalletRemoveRequest")
    List<EWalletDataResponse> eWalletRemoveRequest;
    @SerializedName("Signature")
    String signature;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<EWalletDataResponse> geteWalletRemoveRequest() {
        return eWalletRemoveRequest;
    }

    public void seteWalletRemoveRequest(List<EWalletDataResponse> eWalletRemoveRequest) {
        this.eWalletRemoveRequest = eWalletRemoveRequest;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
