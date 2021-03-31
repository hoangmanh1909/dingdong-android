package com.ems.dingdong.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatisticSMLDeliveryFailResponse {
    @SerializedName("ServiceCode")
    @Expose
    private String serviceCode;
    @SerializedName("ServiceName")
    @Expose
    private String serviceName;
    @SerializedName("Count")
    @Expose
    private int count;
    @SerializedName("AmountCOD")
    @Expose
    private int amountCOD;
    @SerializedName("FeeC")
    @Expose
    private int feeC;
    @SerializedName("FeePPA")
    @Expose
    private int feePPA;

    @SerializedName("ListDetail")
    @Expose
    private List<StatisticSMLDeliveryFailDetailResponse> listDetail;

    public List<StatisticSMLDeliveryFailDetailResponse> getListDetail() {
        return listDetail;
    }

    public void setListDetail(List<StatisticSMLDeliveryFailDetailResponse> listDetail) {
        this.listDetail = listDetail;
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
}
