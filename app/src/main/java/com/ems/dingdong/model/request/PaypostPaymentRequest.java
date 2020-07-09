package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class PaypostPaymentRequest {
    @SerializedName("PostmanID")
    private String postmanID;
    @SerializedName("ParcelCode")
    private String parcelCode;
    @SerializedName("MobileNumber")
    private String mobileNumber;
    @SerializedName("DeliveryPOCode")
    private String deliveryPOCode;
    @SerializedName("DeliveryDate")
    private String deliveryDate;
    @SerializedName("DeliveryTime")
    private String deliveryTime;
    @SerializedName("ReceiverName")
    private String receiverName;
    @SerializedName("ReceiverIDNumber")
    private String receiverIDNumber;
    @SerializedName("ReasonCode")
    private String reasonCode;
    @SerializedName("SolutionCode")
    private String solutionCode;
    @SerializedName("Status")
    private String status;
    @SerializedName("PaymentChannel")
    private String paymentChannel;
    @SerializedName("ShiftID")
    private Integer shiftID;
    @SerializedName("SignatureCapture")
    private String signatureCapture;
    @SerializedName("Note")
    private String note;
    @SerializedName("CollectAmount")
    private Integer collectAmount;
    @SerializedName("Signature")
    private String signature;
    @SerializedName("RouteCode")
    private String routeCode;
    @SerializedName("LadingPostmanID")
    private Integer ladingPostmanID;
    @SerializedName("ImageDelivery")
    private String imageDelivery;
    @SerializedName("PostmanCode")
    private String postmanCode;
    @SerializedName("BatchCode")
    private String batchCode;
    @SerializedName("IsPaymentPP")
    private boolean isPaymentPP;
    @SerializedName("IsItemReturn")
    private String isItemReturn;
    @SerializedName("AmountForBatch")
    private String amountForBatch;
    @SerializedName("ItemsInBatch")
    private Integer itemsInBatch;
    @SerializedName("IsPaymentBatch")
    private boolean isPaymentBatch;
    @SerializedName("LastLadingCode")
    private String lastLadingCode;
    @SerializedName("IsRePaymentBatch")
    private boolean isRePaymentBatch;
    @SerializedName("ReceiverReference")
    private String receiverReference;
    @SerializedName("ReplaceCode")
    private String replaceCode;
    @SerializedName("CustomerCode")
    private String customerCode;
    @SerializedName("ReceiverBirthday")
    private String receiverBirthday;
    @SerializedName("ReceiverPIDDate")
    private String receiverPIDDate;
    @SerializedName("ReceiverPIDWhere")
    private String receiverPIDWhere;
    @SerializedName("ReceiverAddressDetail")
    private String receiverAddressDetail;
    @SerializedName("AuthenType")
    private Integer authenType;
    @SerializedName("ImageAuthen")
    private String ImageAuthen;
    @SerializedName("VATCode")
    private String VATCode;

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

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public void setReceiverBirthday(String receiverBirthday) {
        this.receiverBirthday = receiverBirthday;
    }

    public void setReceiverPIDDate(String receiverPIDDate) {
        this.receiverPIDDate = receiverPIDDate;
    }

    public void setReceiverPIDWhere(String receiverPIDWhere) {
        this.receiverPIDWhere = receiverPIDWhere;
    }

    public void setReceiverAddressDetail(String receiverAddressDetail) {
        this.receiverAddressDetail = receiverAddressDetail;
    }

    public void setAuthenType(Integer authenType) {
        this.authenType = authenType;
    }

    public void setImageAuthen(String imageAuthen) {
        ImageAuthen = imageAuthen;
    }

    public void setVATCode(String VATCode) {
        this.VATCode = VATCode;
    }
}
