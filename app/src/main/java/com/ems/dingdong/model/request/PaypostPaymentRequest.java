package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class PaypostPaymentRequest {
    @SerializedName("PostmanID")
    String postmanID;
    @SerializedName("ParcelCode")
    String parcelCode;
    @SerializedName("MobileNumber")
    String mobileNumber;
    @SerializedName("DeliveryPOCode")
    String deliveryPOCode;
    @SerializedName("DeliveryDate")
    String deliveryDate;
    @SerializedName("DeliveryTime")
    String deliveryTime;
    @SerializedName("ReceiverName")
    String receiverName;
    @SerializedName("ReceiverIDNumber")
    String receiverIDNumber;
    @SerializedName("ReasonCode")
    String reasonCode;
    @SerializedName("SolutionCode")
    String solutionCode;
    @SerializedName("Status")
    String status;
    @SerializedName("PaymentChannel")
    String paymentChannel;
    @SerializedName("ShiftID")
    Integer shiftID;
    @SerializedName("SignatureCapture")
    String signatureCapture;
    @SerializedName("Note")
    String note;
    @SerializedName("CollectAmount")
    Integer collectAmount;
    @SerializedName("Signature")
    String signature;
    @SerializedName("RouteCode")
    String routeCode;
    @SerializedName("LadingPostmanID")
    Integer ladingPostmanID;
    @SerializedName("ImageDelivery")
    String imageDelivery;
    @SerializedName("PostmanCode")
    String postmanCode;
    @SerializedName("BatchCode")
    String batchCode;
    @SerializedName("IsPaymentPP")
    boolean isPaymentPP;
    @SerializedName("IsItemReturn")
    String isItemReturn;
    @SerializedName("AmountForBatch")
    String amountForBatch;
    @SerializedName("ItemsInBatch")
    Integer itemsInBatch;
    @SerializedName("IsPaymentBatch")
    boolean isPaymentBatch;
    @SerializedName("LastLadingCode")
    String lastLadingCode;
    @SerializedName("IsRePaymentBatch")
    boolean isRePaymentBatch;
    @SerializedName("ReceiverReference")
    String receiverReference;
    @SerializedName("ReplaceCode")
    String replaceCode;

    public void setPostmanID(String postmanID) {
        this.postmanID = postmanID;
    }

    public void setParcelCode(String parcelCode) {
        this.parcelCode = parcelCode;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setDeliveryPOCode(String deliveryPOCode) {
        this.deliveryPOCode = deliveryPOCode;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setReceiverIDNumber(String receiverIDNumber) {
        this.receiverIDNumber = receiverIDNumber;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public void setSolutionCode(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public void setShiftID(Integer shiftID) {
        this.shiftID = shiftID;
    }

    public void setSignatureCapture(String signatureCapture) {
        this.signatureCapture = signatureCapture;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCollectAmount(Integer collectAmount) {
        this.collectAmount = collectAmount;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public void setLadingPostmanID(Integer ladingPostmanID) {
        this.ladingPostmanID = ladingPostmanID;
    }

    public void setImageDelivery(String imageDelivery) {
        this.imageDelivery = imageDelivery;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public void setPaymentPP(boolean paymentPP) {
        isPaymentPP = paymentPP;
    }

    public void setIsItemReturn(String isItemReturn) {
        this.isItemReturn = isItemReturn;
    }

    public void setAmountForBatch(String amountForBatch) {
        this.amountForBatch = amountForBatch;
    }

    public void setItemsInBatch(Integer itemsInBatch) {
        this.itemsInBatch = itemsInBatch;
    }

    public void setPaymentBatch(boolean paymentBatch) {
        isPaymentBatch = paymentBatch;
    }

    public void setLastLadingCode(String lastLadingCode) {
        this.lastLadingCode = lastLadingCode;
    }

    public void setRePaymentBatch(boolean rePaymentBatch) {
        isRePaymentBatch = rePaymentBatch;
    }

    public void setReceiverReference(String receiverReference) {
        this.receiverReference = receiverReference;
    }

    public void setReplaceCode(String replaceCode) {
        this.replaceCode = replaceCode;
    }
}
