package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class CancelStatisticItem {
    @SerializedName("LadingCode")
    private String ladingCode;

    @SerializedName("SenderName")
    private String senderName;

    @SerializedName("SenderAddress")
    private String senderAddress;

    @SerializedName("ReceiverName")
    private String receiverName;

    @SerializedName("ReceiverAddress")
    private String receiverAddress;

    @SerializedName("CollectFeeCOD")
    private Integer CollectFeeCOD;
    @SerializedName("FeeCollectLater")
    private Integer FeeCollectLater;

    @SerializedName("Fee")
    private Integer fee;
    @SerializedName("FeePPA")
    private Integer FeePPA;
    @SerializedName("FeePA")
    private Integer FeePA;
    @SerializedName("FeeShip")
    private Integer FeeShip;

    @SerializedName("StatusName")
    private String statusName;

    @SerializedName("LastDateTimeUpdate")
    private String lastDateTimeUpdate;

    @SerializedName("PaymentPayPostStatus")
    private String paymentPayPostStatus;

    @SerializedName("CancelReason")
    private String cancelReason;

    @SerializedName("ReasonTypeName")
    private String reasonTypeName;

    @SerializedName("ReceiveCollectFee")
    private long receiveCollectFee;
    @SerializedName("CODAmount")
    Integer codAmount;

    public Integer getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(Integer codAmount) {
        this.codAmount = codAmount;
    }



    public long getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public void setReceiveCollectFee(long receiveCollectFee) {
        this.receiveCollectFee = receiveCollectFee;
    }

    public Integer getFeePPA() {
        return FeePPA;
    }

    public void setFeePPA(Integer feePPA) {
        FeePPA = feePPA;
    }

    public Integer getFeePA() {
        return FeePA;
    }

    public void setFeePA(Integer feePA) {
        FeePA = feePA;
    }

    public Integer getFeeShip() {
        return FeeShip;
    }

    public void setFeeShip(Integer feeShip) {
        FeeShip = feeShip;
    }

    public Integer getFeeCollectLater() {
        return FeeCollectLater;
    }

    public void setFeeCollectLater(Integer feeCollectLater) {
        FeeCollectLater = feeCollectLater;
    }

    public Integer getCollectFeeCOD() {
        return CollectFeeCOD;
    }

    public void setCollectFeeCOD(Integer collectFeeCOD) {
        CollectFeeCOD = collectFeeCOD;
    }

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }


    public Integer getFee() {
        return fee;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getLastDateTimeUpdate() {
        return lastDateTimeUpdate;
    }

    public String getPaymentPayPostStatus() {
        return paymentPayPostStatus;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public String getReasonTypeName() {
        return reasonTypeName;
    }
}
