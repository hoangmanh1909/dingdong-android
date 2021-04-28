package com.ems.dingdong.model.request;

import com.ems.dingdong.model.ProductModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeliveryProductRequest {

    @SerializedName("LadingCode")
    private String ladingCode;
    @SerializedName("PODeliveryCode")
    private String PODeliveryCode;

    @SerializedName("DeliveryProducts")
    @Expose
    private List<ProductModel> deliveryProducts = null;
    @SerializedName("ReturnProducts")
    @Expose
    private List<ProductModel> returnProducts = null;
    @SerializedName("ReturnImage")
    @Expose
    private String returnImage;
    @SerializedName("DeliveryAmount")
    @Expose
    private Integer deliveryAmount;
    @SerializedName("ReturnAmount")
    @Expose
    private Integer returnAmount;
    @SerializedName("PostmanID")
    @Expose
    private Integer postmanID;
    @SerializedName("ParcelCode")
    @Expose
    private String parcelCode;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("DeliveryPOCode")
    @Expose
    private String deliveryPOCode;
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("DeliveryTime")
    @Expose
    private String deliveryTime;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("ReceiverIDNumber")
    @Expose
    private String receiverIDNumber;
    @SerializedName("ReasonCode")
    @Expose
    private String reasonCode;
    @SerializedName("SolutionCode")
    @Expose
    private String solutionCode;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("PaymentChannel")
    @Expose
    private String paymentChannel;
    @SerializedName("ShiftID")
    @Expose
    private Integer shiftID;
    @SerializedName("SignatureCapture")
    @Expose
    private String signatureCapture;
    @SerializedName("Note")
    @Expose
    private String note;
    @SerializedName("CollectAmount")
    @Expose
    private Integer collectAmount;
    @SerializedName("Signature")
    @Expose
    private String signature;
    @SerializedName("RouteCode")
    @Expose
    private String routeCode;
    @SerializedName("LadingPostmanID")
    @Expose
    private Integer ladingPostmanID;
    @SerializedName("ImageDelivery")
    @Expose
    private String imageDelivery;
    @SerializedName("PostmanCode")
    @Expose
    private String postmanCode;
    @SerializedName("BatchCode")
    @Expose
    private String batchCode;
    @SerializedName("IsPaymentPP")
    @Expose
    private Boolean isPaymentPP;
    @SerializedName("IsItemReturn")
    @Expose
    private String isItemReturn;
    @SerializedName("AmountForBatch")
    @Expose
    private String amountForBatch;
    @SerializedName("ItemsInBatch")
    @Expose
    private Integer itemsInBatch;
    @SerializedName("ReceiverReference")
    @Expose
    private String receiverReference;
    @SerializedName("ReplaceCode")
    @Expose
    private String replaceCode;
    @SerializedName("AuthenType")
    @Expose
    private Integer authenType;
    @SerializedName("ReceiverBirthday")
    @Expose
    private String receiverBirthday;
    @SerializedName("ReceiverPIDDate")
    @Expose
    private String receiverPIDDate;
    @SerializedName("ReceiverPIDWhere")
    @Expose
    private String receiverPIDWhere;
    @SerializedName("ReceiverAddressDetail")
    @Expose
    private String receiverAddressDetail;
    @SerializedName("CustomerCode")
    @Expose
    private String customerCode;
    @SerializedName("ImageAuthen")
    @Expose
    private String imageAuthen;
    @SerializedName("VATCode")
    @Expose
    private String vATCode;

    public List<ProductModel> getDeliveryProducts() {
        return deliveryProducts;
    }

    public void setDeliveryProducts(List<ProductModel> deliveryProducts) {
        this.deliveryProducts = deliveryProducts;
    }

    public List<ProductModel> getReturnProducts() {
        return returnProducts;
    }

    public void setReturnProducts(List<ProductModel> returnProducts) {
        this.returnProducts = returnProducts;
    }

    public String getReturnImage() {
        return returnImage;
    }

    public void setReturnImage(String returnImage) {
        this.returnImage = returnImage;
    }

    public Integer getDeliveryAmount() {
        return deliveryAmount;
    }

    public void setDeliveryAmount(Integer deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public Integer getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Integer returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Integer getPostmanID() {
        return postmanID;
    }

    public void setPostmanID(Integer postmanID) {
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


    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public String getPODeliveryCode() {
        return PODeliveryCode;
    }

    public void setPODeliveryCode(String PODeliveryCode) {
        this.PODeliveryCode = PODeliveryCode;
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

    public Boolean getIsPaymentPP() {
        return isPaymentPP;
    }

    public void setIsPaymentPP(Boolean isPaymentPP) {
        this.isPaymentPP = isPaymentPP;
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

    public Integer getAuthenType() {
        return authenType;
    }

    public void setAuthenType(Integer authenType) {
        this.authenType = authenType;
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

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getImageAuthen() {
        return imageAuthen;
    }

    public void setImageAuthen(String imageAuthen) {
        this.imageAuthen = imageAuthen;
    }

    public String getVATCode() {
        return vATCode;
    }

    public void setVATCode(String vATCode) {
        this.vATCode = vATCode;
    }
}

