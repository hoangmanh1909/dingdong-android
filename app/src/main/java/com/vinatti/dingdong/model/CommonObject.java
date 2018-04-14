package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class CommonObject {

    //region xacnhantin
    @SerializedName("OrderPostmanID")
    String orderPostmanID;
    @SerializedName("Count")
    String count;
    @SerializedName("Description")
    String description;
    @SerializedName("AssignDateTime")
    String assignDateTime;
    @SerializedName("AssignFullName")
    String assignFullName;
    @SerializedName("Quantity")
    String quantity;
    //endregion
    //region common
    @SerializedName("StatusCode")
    String statusCode;
    @SerializedName(value = "Code", alternate = {"MaE"})
    String code;
    @SerializedName(value = "ID", alternate = {"Id"})
    String iD;
    @SerializedName(value = "ContactName", alternate = {"ReciverName"})
    String contactName;
    @SerializedName(value = "ContactPhone", alternate = {"ReciverMobile"})
    String contactPhone;
    @SerializedName(value = "ContactAddress", alternate = {"ReciverAddress"})
    String contactAddress;
    @SerializedName(value = "Weigh", alternate = {"Weight"})
    String weigh;
    //endregion
    //region baophatbangke
    @SerializedName("SenderName")
    String senderName;
    @SerializedName("SenderAddress")
    String senderAddress;
    @SerializedName("PoCode")
    String poCode;
    @SerializedName("CreateDate")
    String createDate;
    @SerializedName("Route")
    String route;
    @SerializedName("Order")
    String order;



    private String realReceiverName;
    private String realReceiverIDNumber;
    private String DeliveryType;
    private String userDelivery;
    private String currentPaymentType;
    //endregion
    @SerializedName("IsCOD")
    String isCOD;
    private String deliveryDate;
    private String deliveryTime;
    private String collectAmount;

    public String getCount() {
        return count;
    }

    public String getCode() {
        return code;
    }

    public String getParcelCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getContactName() {
        return contactName;
    }

    public String getReciverName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getReciverAddress() {
        return contactAddress;
    }

    public String getOrderPostmanID() {
        return orderPostmanID;
    }

    public String getAssignDateTime() {
        return assignDateTime;
    }

    public String getAssignFullName() {
        return assignFullName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeigh() {
        return weigh;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getiD() {
        return iD;
    }

    public String getRoute() {
        return route;
    }

    public String getOrder() {
        return order;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public String getPoCode() {
        return poCode;
    }

    public String getIssuePOCode() {
        return poCode;
    }

    public String getCreateDate() {
        return createDate;
    }


    public void setRealReceiverName(String realReceiverName) {
        this.realReceiverName = realReceiverName;
    }

    public void setRealReceiverIDNumber(String realReceiverIDNumber) {
        this.realReceiverIDNumber = realReceiverIDNumber;
    }

    public String getDeliveryType() {
        return DeliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        DeliveryType = deliveryType;
    }

    public void setUserDelivery(String userDelivery) {
        this.userDelivery = userDelivery;
    }

    public void setCurrentPaymentType(String currentPaymentType) {
        this.currentPaymentType = currentPaymentType;
    }

    public String getRealReceiverName() {
        return realReceiverName;
    }

    public String getRealReceiverIDNumber() {
        return realReceiverIDNumber;
    }

    public String getUserDelivery() {
        return userDelivery;
    }

    public String getCurrentPaymentType() {
        return currentPaymentType;
    }

    public String getIsCOD() {
        return isCOD;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public String getCollectAmount() {
        return collectAmount;
    }
}
