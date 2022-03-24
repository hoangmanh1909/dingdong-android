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

    @SerializedName("FeePA")
    private long FeePA;
    @SerializedName("FeeCOD")
    private long FeeCOD;
    @SerializedName("FeePAPNS")
    private long FeePAPNS;
    @SerializedName("ServiceCode ")
    private String ServiceCode;

    @SerializedName("IsEditCODAmount")
    private boolean IsEditCODAmount;
    @SerializedName("CODAmountEdit")
    private long cODAmountEdit;

    @SerializedName("DeliveryLat")
    private String DeliveryLat;
    @SerializedName("DeliveryLon")
    private String DeliveryLon;
    @SerializedName("ReceiverLat")
    private String ReceiverLat;
    @SerializedName("ReceiverLon")
    private String ReceiverLon;
    @SerializedName("PODeliveryLat")
    private String PODeliveryLat;
    @SerializedName("PODeliveryLon")
    private String PODeliveryLon;
    @SerializedName("SourceChanel")
    private String SourceChanel = "DD_ANDROID";

    public String getSourceChanel() {
        return SourceChanel;
    }

    public void setSourceChanel(String sourceChanel) {
        SourceChanel = sourceChanel;
    }

    public long getFeeCOD() {
        return FeeCOD;
    }

    public void setFeeCOD(long feeCOD) {
        FeeCOD = feeCOD;
    }

    public String getDeliveryLat() {
        return DeliveryLat;
    }

    public void setDeliveryLat(String deliveryLat) {
        DeliveryLat = deliveryLat;
    }

    public String getDeliveryLon() {
        return DeliveryLon;
    }

    public void setDeliveryLon(String deliveryLon) {
        DeliveryLon = deliveryLon;
    }

    public String getReceiverLat() {
        return ReceiverLat;
    }

    public void setReceiverLat(String receiverLat) {
        ReceiverLat = receiverLat;
    }

    public String getReceiverLon() {
        return ReceiverLon;
    }

    public void setReceiverLon(String receiverLon) {
        ReceiverLon = receiverLon;
    }

    public String getPODeliveryLat() {
        return PODeliveryLat;
    }

    public void setPODeliveryLat(String PODeliveryLat) {
        this.PODeliveryLat = PODeliveryLat;
    }

    public String getPODeliveryLon() {
        return PODeliveryLon;
    }

    public void setPODeliveryLon(String PODeliveryLon) {
        this.PODeliveryLon = PODeliveryLon;
    }

    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
    }

    public void setEditCODAmount(boolean editCODAmount) {
        IsEditCODAmount = editCODAmount;
    }

    public void setcODAmountEdit(long cODAmountEdit) {
        this.cODAmountEdit = cODAmountEdit;
    }

    public long getFeePAPNS() {
        return FeePAPNS;
    }

    public void setFeePAPNS(long feePAPNS) {
        FeePAPNS = feePAPNS;
    }

    public long getFeePA() {
        return FeePA;
    }

    public void setFeePA(long feePA) {
        FeePA = feePA;
    }

    public long getFeePPA() {
        return feePPA;
    }

    public void setFeePPA(long feePPA) {
        this.feePPA = feePPA;
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

    public String getIsItemReturn() {
        return isItemReturn;
    }
}
