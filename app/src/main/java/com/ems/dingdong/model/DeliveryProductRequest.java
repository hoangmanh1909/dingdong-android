package com.ems.dingdong.model;

import com.google.android.gms.vision.L;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryProductRequest {
    ///
    @SerializedName("DeliveryProducts")
    private List<DeliveryListRelease> listProduct;
    @SerializedName("ReturnProducts")
    private List<DeliveryListReturn> listReturn;

    @SerializedName("ReturnImage")
    private String returnImage;
    @SerializedName("DeliveryAmount")
    private Long deliveryAmount;
    @SerializedName("ReturnAmount")
    private Long returnAmount;
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

    public List<DeliveryListRelease> getListProduct() {
        return listProduct;
    }


    public void setListProduct(List<DeliveryListRelease> listProduct) {
        this.listProduct = listProduct;
    }

    public List<DeliveryListReturn> getListReturn() {
        return listReturn;
    }

    public void setListReturn(List<DeliveryListReturn> listReturn) {
        this.listReturn = listReturn;
    }

    public String getReturnImage() {
        return returnImage;
    }

    public void setReturnImage(String returnImage) {
        this.returnImage = returnImage;
    }

    public Long getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(Long deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public Long getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Long returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getPostmanID() {
        return postmanID;
    }

    public void setPostmanID(String postmanID) {
        this.postmanID = postmanID;
    }

    public String getParcelCode() {
        return parcelCode;
    }

    public void setParcelCode(String parcelCode) {
        this.parcelCode = parcelCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDeliveryPOCode() {
        return deliveryPOCode;
    }

    public void setDeliveryPOCode(String deliveryPOCode) {
        this.deliveryPOCode = deliveryPOCode;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverIDNumber() {
        return receiverIDNumber;
    }

    public void setReceiverIDNumber(String receiverIDNumber) {
        this.receiverIDNumber = receiverIDNumber;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getSolutionCode() {
        return solutionCode;
    }

    public void setSolutionCode(String solutionCode) {
        this.solutionCode = solutionCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Integer getShiftID() {
        return shiftID;
    }

    public void setShiftID(Integer shiftID) {
        this.shiftID = shiftID;
    }

    public String getSignatureCapture() {
        return signatureCapture;
    }

    public void setSignatureCapture(String signatureCapture) {
        this.signatureCapture = signatureCapture;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCollectAmount() {
        return collectAmount;
    }

    public void setCollectAmount(Integer collectAmount) {
        this.collectAmount = collectAmount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public Integer getLadingPostmanID() {
        return ladingPostmanID;
    }

    public void setLadingPostmanID(Integer ladingPostmanID) {
        this.ladingPostmanID = ladingPostmanID;
    }

    public String getImageDelivery() {
        return imageDelivery;
    }

    public void setImageDelivery(String imageDelivery) {
        this.imageDelivery = imageDelivery;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public boolean isPaymentPP() {
        return isPaymentPP;
    }

    public void setPaymentPP(boolean paymentPP) {
        isPaymentPP = paymentPP;
    }

    public String getIsItemReturn() {
        return isItemReturn;
    }

    public void setIsItemReturn(String isItemReturn) {
        this.isItemReturn = isItemReturn;
    }

    public String getAmountForBatch() {
        return amountForBatch;
    }

    public void setAmountForBatch(String amountForBatch) {
        this.amountForBatch = amountForBatch;
    }

    public Integer getItemsInBatch() {
        return itemsInBatch;
    }

    public void setItemsInBatch(Integer itemsInBatch) {
        this.itemsInBatch = itemsInBatch;
    }

    public boolean isPaymentBatch() {
        return isPaymentBatch;
    }

    public void setPaymentBatch(boolean paymentBatch) {
        isPaymentBatch = paymentBatch;
    }

    public String getLastLadingCode() {
        return lastLadingCode;
    }

    public void setLastLadingCode(String lastLadingCode) {
        this.lastLadingCode = lastLadingCode;
    }

    public boolean isRePaymentBatch() {
        return isRePaymentBatch;
    }

    public void setRePaymentBatch(boolean rePaymentBatch) {
        isRePaymentBatch = rePaymentBatch;
    }

    public String getReceiverReference() {
        return receiverReference;
    }

    public void setReceiverReference(String receiverReference) {
        this.receiverReference = receiverReference;
    }

    public String getReplaceCode() {
        return replaceCode;
    }

    public void setReplaceCode(String replaceCode) {
        this.replaceCode = replaceCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getReceiverBirthday() {
        return receiverBirthday;
    }

    public void setReceiverBirthday(String receiverBirthday) {
        this.receiverBirthday = receiverBirthday;
    }

    public String getReceiverPIDDate() {
        return receiverPIDDate;
    }

    public void setReceiverPIDDate(String receiverPIDDate) {
        this.receiverPIDDate = receiverPIDDate;
    }

    public String getReceiverPIDWhere() {
        return receiverPIDWhere;
    }

    public void setReceiverPIDWhere(String receiverPIDWhere) {
        this.receiverPIDWhere = receiverPIDWhere;
    }

    public String getReceiverAddressDetail() {
        return receiverAddressDetail;
    }

    public void setReceiverAddressDetail(String receiverAddressDetail) {
        this.receiverAddressDetail = receiverAddressDetail;
    }

    public Integer getAuthenType() {
        return authenType;
    }

    public void setAuthenType(Integer authenType) {
        this.authenType = authenType;
    }

    public String getImageAuthen() {
        return ImageAuthen;
    }

    public void setImageAuthen(String imageAuthen) {
        ImageAuthen = imageAuthen;
    }

    public String getVATCode() {
        return VATCode;
    }

    public void setVATCode(String VATCode) {
        this.VATCode = VATCode;
    }
}
