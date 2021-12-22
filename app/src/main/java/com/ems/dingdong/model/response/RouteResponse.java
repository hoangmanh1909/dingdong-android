package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class RouteResponse {
    @SerializedName("Id")
    Integer id;
    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("FromRouteId")
    Integer fromRouteId;
    @SerializedName("FromRouteName")
    String fromRouteName;
    @SerializedName("FromPostmanId")
    Integer fromPostmanId;
    @SerializedName("FromPostmanName")
    String fromPostmanName;

    @SerializedName("ToRouteId")
    Integer toRouteId;
    @SerializedName("ToRouteName")
    String toRouteName;
    @SerializedName("ToPostmanId")
    Integer toPostmanId;
    @SerializedName("ToPostmanName")
    String toPostmanName;

    @SerializedName("StatusCode")
    String statusCode;
    @SerializedName("StatusName")
    String statusName;

    @SerializedName("StatusDate")
    String statusDate;
    @SerializedName("CODAmount")
    Integer codAmount;
    @SerializedName("Fee")
    Integer fee;


    @SerializedName("ToPOCode")
    String ToPOCode;
    @SerializedName("ToPOName")
    String ToPOName;


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
    private long receiveCollectFee;

    public long getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public void setReceiveCollectFee(long receiveCollectFee) {
        this.receiveCollectFee = receiveCollectFee;
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

    public String getToPOCode() {
        return ToPOCode;
    }

    public void setToPOCode(String toPOCode) {
        ToPOCode = toPOCode;
    }

    public String getToPOName() {
        return ToPOName;
    }

    public void setToPOName(String toPOName) {
        ToPOName = toPOName;
    }

    public Integer getId() {
        return id;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public Integer getFromRouteId() {
        return fromRouteId;
    }

    public String getFromRouteName() {
        return fromRouteName;
    }

    public Integer getFromPostmanId() {
        return fromPostmanId;
    }

    public String getFromPostmanName() {
        return fromPostmanName;
    }

    public Integer getToRouteId() {
        return toRouteId;
    }

    public String getToRouteName() {
        return toRouteName;
    }

    public Integer getToPostmanId() {
        return toPostmanId;
    }

    public String getToPostmanName() {
        return toPostmanName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public Integer getCodAmount() {
        return codAmount;
    }

    public Integer getFee() {
        return fee;
    }

    public String getStatusDate() {
        return statusDate;
    }
}
