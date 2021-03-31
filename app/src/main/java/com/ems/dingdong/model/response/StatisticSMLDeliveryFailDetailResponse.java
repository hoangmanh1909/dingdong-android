package com.ems.dingdong.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatisticSMLDeliveryFailDetailResponse {
    @SerializedName("ServiceCode")
    @Expose
    private String serviceCode;
    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("LadingCode")
    @Expose
    private String ladingCode;
    @SerializedName("AmountCOD")
    @Expose
    private int amountCOD;
    @SerializedName("FeeC")
    @Expose
    private int feeC;
    @SerializedName("FeePPA")
    @Expose
    private int feePPA;
    @SerializedName("SMLHubCode")
    @Expose
    private String sMLHubCode;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public int getAmountCOD() {
        return amountCOD;
    }

    public void setAmountCOD(int amountCOD) {
        this.amountCOD = amountCOD;
    }

    public int getFeeC() {
        return feeC;
    }

    public void setFeeC(int feeC) {
        this.feeC = feeC;
    }

    public int getFeePPA() {
        return feePPA;
    }

    public void setFeePPA(int feePPA) {
        this.feePPA = feePPA;
    }

    public String getSMLHubCode() {
        return sMLHubCode;
    }

    public void setSMLHubCode(String sMLHubCode) {
        this.sMLHubCode = sMLHubCode;
    }
}
