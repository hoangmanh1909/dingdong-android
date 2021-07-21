package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class PaymentDeviveryRequest {
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
    @SerializedName("DeliveryType")
    String deliveryType;
    @SerializedName("SignatureCapture")
    String signatureCapture;
    @SerializedName("Note")
    String note;
    @SerializedName("CollectAmount")
    String collectAmount;
    @SerializedName("ShiftID")
    String shiftID;
    @SerializedName("RouteCode")
    String routeCode;
    @SerializedName("LadingPostmanID")
    String ladingPostmanID;
    @SerializedName("Signature")
    String signature;
    @SerializedName("ImageDelivery")
    String imageDelivery;/*
    @SerializedName("ImageAuthen")
    String imageAuthen;*/
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
    @SerializedName("ReceiverReference")
    String receiverReference;

    @SerializedName("FeePPA")
    private long feePPA;
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


    public PaymentDeviveryRequest(String postmanID, String parcelCode, String mobileNumber, String deliveryPOCode, String deliveryDate,
                                  String deliveryTime, String receiverName, String receiverIDNumber, String reasonCode, String solutionCode,
                                  String status, String paymentChannel, String deliveryType, String signatureCapture, String note, String collectAmount,
                                  String shiftID, String routeCode, String ladingPostmanID, String signature, String imageDelivery, String postmanCode,
                                  String batchCode, boolean isPaymentPP, String isItemReturn, String amountForBatch, Integer itemsInBatch, long feePPA
            , long feeShip, long feeCollectLater, long feePPAPNS, long feeShipPNS, long feeCollectLaterPNS) {
        this.postmanID = postmanID;
        this.parcelCode = parcelCode;
        this.mobileNumber = mobileNumber;
        this.deliveryPOCode = deliveryPOCode;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.receiverName = receiverName;
        this.receiverIDNumber = receiverIDNumber;
        this.reasonCode = reasonCode;
        this.solutionCode = solutionCode;
        this.status = status;
        this.paymentChannel = paymentChannel;
        this.deliveryType = deliveryType;
        this.signatureCapture = signatureCapture;
        this.note = note;
        this.collectAmount = collectAmount;
        this.shiftID = shiftID;
        this.routeCode = routeCode;
        this.ladingPostmanID = ladingPostmanID;
        this.signature = signature;
        this.imageDelivery = imageDelivery;
        //this.imageAuthen = imageAuthen;
        this.batchCode = batchCode;
        this.postmanCode = postmanCode;
        this.isPaymentPP = isPaymentPP;
        this.isItemReturn = isItemReturn;
        this.amountForBatch = amountForBatch;
        this.itemsInBatch = itemsInBatch;
        this.feePPA = feePPA;
        this.feeShip = feeShip;
        this.feeCollectLater = feeCollectLater;
        this.feePPAPNS = feePPAPNS;
        this.feeShipPNS = feeShipPNS;
        this.feeCollectLaterPNS = feeCollectLaterPNS;
    }

    public void setReceiverReference(String receiverReference) {
        this.receiverReference = receiverReference;
    }

    public void setImageDelivery(String imageDelivery) {
        this.imageDelivery = imageDelivery;
    }

    /*public void setImageAuthen(String imageAuthen) {
        this.imageAuthen = imageAuthen;
    }*/
}
