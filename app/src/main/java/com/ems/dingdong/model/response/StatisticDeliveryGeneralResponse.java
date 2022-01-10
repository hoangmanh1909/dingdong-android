package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class StatisticDeliveryGeneralResponse {
    @SerializedName("ServiceCode")
    private String serviceCode;
    @SerializedName("ServiceName")
    private String serviceName;
    @SerializedName("Quantity")
    private String quantity;
    @SerializedName("QuantityCOD")
    private String quantityCOD;
    @SerializedName("QuantityC")
    private String quantityC;
    @SerializedName("QuantityPPA")
    private String quantityPPA;

    @SerializedName("FeePPA")
    private long feePPA;
    @SerializedName("FeePA")
    private long FeePA;
    @SerializedName("FeeShip")
    private long feeShip;
    @SerializedName("FeeCollectLater")
    private long feeCollectLater;
    @SerializedName("FeePPAPNS")
    private long feePPAPNS;
    @SerializedName("FeeShipPNS")
    private long feeShipPNS;
    @SerializedName("FeeCollectLaterPNS")
    private long feeCollectLaterPNS;
    @SerializedName("IsCancelOrder")
    private boolean isCancelOrder;
    @SerializedName("FeeCancelOrder")
    private long feeCancelOrder;
    @SerializedName("ReceiveCollectFee")
    private String receiveCollectFee;


    long tongtien ;

    public long getTongtien() {
        return tongtien;
    }

    public void setTongtien(long tongtien) {
        this.tongtien = tongtien;
    }

    public String getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public void setReceiveCollectFee(String receiveCollectFee) {
        this.receiveCollectFee = receiveCollectFee;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public long getFeePPA() {
        return feePPA;
    }

    public void setFeePPA(long feePPA) {
        this.feePPA = feePPA;
    }

    public long getFeePA() {
        return FeePA;
    }

    public void setFeePA(long feePA) {
        FeePA = feePA;
    }

    public long getFeeShip() {
        return feeShip;
    }

    public void setFeeShip(long feeShip) {
        this.feeShip = feeShip;
    }

    public long getFeeCollectLater() {
        return feeCollectLater;
    }

    public void setFeeCollectLater(long feeCollectLater) {
        this.feeCollectLater = feeCollectLater;
    }

    public long getFeePPAPNS() {
        return feePPAPNS;
    }

    public void setFeePPAPNS(long feePPAPNS) {
        this.feePPAPNS = feePPAPNS;
    }

    public long getFeeShipPNS() {
        return feeShipPNS;
    }

    public void setFeeShipPNS(long feeShipPNS) {
        this.feeShipPNS = feeShipPNS;
    }

    public long getFeeCollectLaterPNS() {
        return feeCollectLaterPNS;
    }

    public void setFeeCollectLaterPNS(long feeCollectLaterPNS) {
        this.feeCollectLaterPNS = feeCollectLaterPNS;
    }

    public boolean isCancelOrder() {
        return isCancelOrder;
    }

    public void setCancelOrder(boolean cancelOrder) {
        isCancelOrder = cancelOrder;
    }

    public long getFeeCancelOrder() {
        return feeCancelOrder;
    }

    public void setFeeCancelOrder(long feeCancelOrder) {
        this.feeCancelOrder = feeCancelOrder;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getQuantityCOD() {
        return quantityCOD;
    }

    public String getQuantityC() {
        return quantityC;
    }

    public String getQuantityPPA() {
        return quantityPPA;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setQuantityCOD(String quantityCOD) {
        this.quantityCOD = quantityCOD;
    }

    public void setQuantityC(String quantityC) {
        this.quantityC = quantityC;
    }

    public void setQuantityPPA(String quantityPPA) {
        this.quantityPPA = quantityPPA;
    }
}
