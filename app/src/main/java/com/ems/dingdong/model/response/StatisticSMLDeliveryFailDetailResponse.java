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
 @SerializedName("ExpirationDate")
    @Expose
    private String ExpirationDate;

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
    public String getServiceCode() {
        return serviceCode;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }

    public String getsMLHubCode() {
        return sMLHubCode;
    }

    public void setsMLHubCode(String sMLHubCode) {
        this.sMLHubCode = sMLHubCode;
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

    public String getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public void setReceiveCollectFee(String receiveCollectFee) {
        this.receiveCollectFee = receiveCollectFee;
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
