package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DetailLadingCode {
    @SerializedName("Count")
    Integer count;
    @SerializedName("Id")
    Integer id;
    @SerializedName("MaE")
    String laddingCode;
    @SerializedName("SenderName")
    String senderName;
    @SerializedName("SenderMobile")
    String senderMobile;
    @SerializedName("SenderAddress")
    String senderAddress;
    @SerializedName("ReciverName")
    String receiverName;
    @SerializedName("ReciverAddress")
    String receiverAddress;
    @SerializedName("ReciverMobile")
    String receiverMobile;
    @SerializedName("PoCode")
    String poCode;
    @SerializedName("Weight")
    Integer weight;
    @SerializedName("CreateDate")
    String createDate;
    @SerializedName("Route")
    Integer route;
    @SerializedName("Order")
    Integer order;
    @SerializedName("Amount")
    Integer amount;
    @SerializedName("Service")
    String service;
    @SerializedName("ServiceName")
    String serviceName;
    @SerializedName("Info")
    String info;
    @SerializedName("Note")
    String note;
    @SerializedName("Status")
    String status;
    @SerializedName("Instruction")
    String instruction;
    @SerializedName("AutoCallStatus")
    String autoCallStatus;
    @SerializedName("RouteId")
    String routeId;
    @SerializedName("RouteCode")
    String routeCode;
    @SerializedName("ShiftId")
    String shiftId;
    @SerializedName("TotalFee")
    Integer totalFee;
    @SerializedName("NewInstruction")
    String newInstruction;
    @SerializedName("VATCode")
    String VATCode;
    @SerializedName("Description")
    String description;
    @SerializedName("BatchCode")
    String batchCode;
    @SerializedName("NewReceiverAddress")
    String newReceiverAddress;
    @SerializedName("ChangeRouteStatus")
    String changeRouteStatus;
    @SerializedName("BD13CreatedDate")
    String bD13CreatedDate;
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

    public long getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public void setReceiveCollectFee(long receiveCollectFee) {
        this.receiveCollectFee = receiveCollectFee;
    }

    public String getbD13CreatedDate() {
        return bD13CreatedDate;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getId() {
        return id;
    }

    public String getLaddingCode() {
        return laddingCode;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public String getPoCode() {
        return poCode;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Integer getRoute() {
        return route;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getService() {
        return service;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getInfo() {
        return info;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getAutoCallStatus() {
        return autoCallStatus;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getShiftId() {
        return shiftId;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public String getNewInstruction() {
        return newInstruction;
    }

    public String getVATCode() {
        return VATCode;
    }

    public String getDescription() {
        return description;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public String getNewReceiverAddress() {
        return newReceiverAddress;
    }

    public String getChangeRouteStatus() {
        return changeRouteStatus;
    }
}
