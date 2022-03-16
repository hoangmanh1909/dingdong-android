package com.ems.dingdong.model.response;

import com.ems.dingdong.model.SimpleResult;
import com.google.gson.annotations.SerializedName;

public class EWalletDataResponse extends SimpleResult {

    @SerializedName("ReceiverName")
    String receiverName;

    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("ReceiverAddress")
    String receiverAddress;

    @SerializedName("CODAmount")
    Integer codAmount;

    @SerializedName("Fee")
    Integer fee;

    @SerializedName("StatusName")
    String statusName;

    @SerializedName("StatusCode")
    String statusCode;

    @SerializedName("RetRefNumber")
    String retRefNumber;

    @SerializedName("RemoveBy")
    String removeBy;

    @SerializedName("TransDate")
    String transDate;

    @SerializedName("FeeType")
    String FeeType;

    @SerializedName("ServiceCode")
    String ServiceCode;
    @SerializedName("EWalletTransId")
    String EWalletTransId;

    @SerializedName("FeeTypeName")
    String FeeTypeName;

    @SerializedName("CashinChannel")
    String CashinChannel;

    public String getCashinChannel() {
        return CashinChannel;
    }

    public void setCashinChannel(String cashinChannel) {
        CashinChannel = cashinChannel;
    }

    public String getFeeTypeName() {
        return FeeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        FeeTypeName = feeTypeName;
    }

    public String getEWalletTransId() {
        return EWalletTransId;
    }

    public void setEWalletTransId(String EWalletTransId) {
        this.EWalletTransId = EWalletTransId;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
    }

    public String getFeeType() {
        return FeeType;
    }

    public void setFeeType(String feeType) {
        FeeType = feeType;
    }

    int getPositionTab;

    public int getGetPositionTab() {
        return getPositionTab;
    }

    public void setGetPositionTab(int getPositionTab) {
        this.getPositionTab = getPositionTab;
    }

    public String getTransDate() {
        return transDate;
    }

    public String getRemoveBy() {
        return removeBy;
    }

    public void setRemoveBy(String removeBy) {
        this.removeBy = removeBy;
    }

    public String getRetRefNumber() {
        return retRefNumber;
    }

    public void setRetRefNumber(String retRefNumber) {
        this.retRefNumber = retRefNumber;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    boolean isSelected;

    public String getLadingCode() {
        return ladingCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public Integer getCodAmount() {
        return codAmount;
    }

    public Integer getFee() {
        return fee;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
