package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class PushToPnsRequest {
    @SerializedName("PostmanID")
    String postmanID;
    @SerializedName("LadingCode")
    String ladingCode;


    @SerializedName("DeliveryPOCode")
    String deliveryPOCode;
    @SerializedName("DeliveryDate")
    String deliveryDate;
    @SerializedName("DeliveryTime")
    String deliveryTime;
    @SerializedName("ReceiverName")
    String receiverName;
    @SerializedName("ReasonCode")
    String reasonCode;
    @SerializedName("SolutionCode")
    String solutionCode;
    @SerializedName("Status")
    String status;
    @SerializedName("PaymentChannel")
    String paymentChannel;
    @SerializedName("DeliveryType")
    String deliveryType;
    @SerializedName("SignatureCapture")
    String signatureCapture;
    @SerializedName("Note")
    String note;
    @SerializedName("CollectAmount")
    String collectAmount;
    @SerializedName("LadingPostmanID")
    String ladingPostmanID;
    @SerializedName("ShiftID")
    String shiftID;
    @SerializedName("RouteCode")
    String routeCode;
    @SerializedName("Signature")
    String signature;
    @SerializedName("ImageDelivery")
    String imageDelivery;
    @SerializedName("IsItemReturn")
    String isItemReturn;

    @SerializedName("BatchCode")
    String batchCode;
    @SerializedName("AmountForBatch")
    String amountForBatch;
    @SerializedName("ItemsInBatch")
    Integer itemsInBatch;
    @SerializedName("CustomerCode")
    private String customerCode;
    @SerializedName("VATCode")
    private String VATCode;

    public PushToPnsRequest(String postmanID, String ladingCode, String deliveryPOCode, String deliveryDate, String deliveryTime, String receiverName,
                            String reasonCode, String solutionCode, String status, String paymentChannel,
                            String deliveryType, String signatureCapture, String note, String collectAmount, String ladingPostmanID,
                            String shiftID, String routeCode, String signature, String imageDelivery, String isItemReturn, String batchCode,
                            Integer itemsInBatch, String amountForBatch) {
        this.postmanID = postmanID;
        this.ladingCode = ladingCode;
        this.deliveryPOCode = deliveryPOCode;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.receiverName = receiverName;
        this.reasonCode = reasonCode;
        this.solutionCode = solutionCode;
        this.status = status;
        this.paymentChannel = paymentChannel;
        this.deliveryType = deliveryType;
        this.signatureCapture = signatureCapture;
        this.note = note;
        this.collectAmount = collectAmount;
        this.ladingPostmanID = ladingPostmanID;
        this.shiftID = shiftID;
        this.routeCode = routeCode;
        this.signature = signature;
        this.imageDelivery = imageDelivery;
        this.isItemReturn = isItemReturn;
        this.batchCode = batchCode;
        this.itemsInBatch = itemsInBatch;
        this.amountForBatch = amountForBatch;
    }


    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public void setVATCode(String VATCode) {
        this.VATCode = VATCode;
    }

    public void setImageDelivery(String imageDelivery) {
        this.imageDelivery = imageDelivery;
    }
}
