package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class CancelStatisticItem {
    @SerializedName("LadingCode")
    private String ladingCode;

    @SerializedName("SenderName")
    private String senderName;

    @SerializedName("ReceiverName")
    private String receiverName;

    @SerializedName("ReceiverAddress")
    private String receiverAddress;

    @SerializedName("CODAmount")
    private Integer cODAmount;

    @SerializedName("Fee")
    private Integer fee;

    @SerializedName("StatusName")
    private String statusName;

    @SerializedName("LastDateTimeUpdate")
    private String lastDateTimeUpdate;

    @SerializedName("PaymentPayPostStatus")
    private String paymentPayPostStatus;

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

    public Integer getcODAmount() {
        return cODAmount;
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
}
