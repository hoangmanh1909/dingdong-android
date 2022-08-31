package com.ems.dingdong.model.response;

import com.google.gson.annotations.SerializedName;

public class ChuaPhanHuongMode {
    @SerializedName("Id")
    private Integer Id;


    @SerializedName("LadingCode")
    private String LadingCode;

    @SerializedName("ReceiverName")
    private String ReceiverName;
    @SerializedName("ReceiverTel")
    private String ReceiverTel;
    @SerializedName("ReceiverAddress")
    private String ReceiverAddress;
    @SerializedName("ReceiverLat")
    private double ReceiverLat;
    @SerializedName("ReceiverLon")
    private double ReceiverLon;
    @SerializedName("SenderName")
    private String SenderName;
    @SerializedName("SenderTel")
    private String SenderTel;
    @SerializedName("SenderAddress")
    private String SenderAddress;

    int STT = 0;
    @SerializedName("Content")
    private String Content;
    @SerializedName("Weight")
    private String Weight;
    @SerializedName("AmountCOD")
    private Integer AmountCOD;

    @SerializedName("FeeCOD")
    private Integer FeeCOD;

    @SerializedName("FeeC")
    private Integer FeeC;

    @SerializedName("FeePPA")
    private Integer FeePPA;

    @SerializedName("FeeShip")
    private Integer FeeShip;
    @SerializedName("FeeCancelOrder")
    private Integer FeeCancelOrder;
    @SerializedName("FeeCollectLater")
    private Integer FeeCollectLater;

    @SerializedName("VATCode")
    private String VATCode;

    public double getReceiverLat() {
        return ReceiverLat;
    }

    public void setReceiverLat(double receiverLat) {
        ReceiverLat = receiverLat;
    }

    public double getReceiverLon() {
        return ReceiverLon;
    }

    public void setReceiverLon(double receiverLon) {
        ReceiverLon = receiverLon;
    }

    public int getSTT() {
        return STT;
    }

    public void setSTT(int STT) {
        this.STT = STT;
    }

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getReceiverTel() {
        return ReceiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        ReceiverTel = receiverTel;
    }

    public String getReceiverAddress() {
        return ReceiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        ReceiverAddress = receiverAddress;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getSenderTel() {
        return SenderTel;
    }

    public void setSenderTel(String senderTel) {
        SenderTel = senderTel;
    }

    public String getSenderAddress() {
        return SenderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        SenderAddress = senderAddress;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public Integer getAmountCOD() {
        return AmountCOD;
    }

    public void setAmountCOD(Integer amountCOD) {
        AmountCOD = amountCOD;
    }

    public Integer getFeeCOD() {
        return FeeCOD;
    }

    public void setFeeCOD(Integer feeCOD) {
        FeeCOD = feeCOD;
    }

    public Integer getFeeC() {
        return FeeC;
    }

    public void setFeeC(Integer feeC) {
        FeeC = feeC;
    }

    public Integer getFeePPA() {
        return FeePPA;
    }

    public void setFeePPA(Integer feePPA) {
        FeePPA = feePPA;
    }

    public Integer getFeeShip() {
        return FeeShip;
    }

    public void setFeeShip(Integer feeShip) {
        FeeShip = feeShip;
    }

    public Integer getFeeCancelOrder() {
        return FeeCancelOrder;
    }

    public void setFeeCancelOrder(Integer feeCancelOrder) {
        FeeCancelOrder = feeCancelOrder;
    }

    public Integer getFeeCollectLater() {
        return FeeCollectLater;
    }

    public void setFeeCollectLater(Integer feeCollectLater) {
        FeeCollectLater = feeCollectLater;
    }

    public String getVATCode() {
        return VATCode;
    }

    public void setVATCode(String VATCode) {
        this.VATCode = VATCode;
    }
}
