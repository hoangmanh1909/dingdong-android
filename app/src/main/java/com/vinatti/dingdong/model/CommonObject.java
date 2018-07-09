package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
    @SerializedName(value = "Code", alternate = {"MaE", "ParcelCode"})
    String code;
    @SerializedName(value = "ID", alternate = {"Id"})
    String iD;
    @SerializedName(value = "ContactName", alternate = {"ReciverName", "ReceiverName"})
    String contactName;
    @SerializedName(value = "ContactPhone", alternate = {"ReciverMobile", "ReceiverPhone"})
    String contactPhone;
    @SerializedName(value = "ContactAddress", alternate = {"ReciverAddress", "ReceiverAddress"})
    String contactAddress;
    @SerializedName(value = "Weigh", alternate = {"Weight"})
    String weigh;
    //endregion
    //region baophatbangke
    @SerializedName("SenderName")
    String senderName;
    @SerializedName(value = "SenderPhone", alternate = {"SenderMobile"})
    String senderPhone;
    @SerializedName("SenderAddress")
    String senderAddress;
    @SerializedName("PoCode")
    String poCode;
    @SerializedName(value = "CreateDate")
    String createDate;
    @SerializedName("LoadDate")
    String loadDate;
    @SerializedName("Route")
    String route;
    @SerializedName("Order")
    String order;
    @SerializedName("SignatureCapture")
    String signatureCapture;
    @SerializedName("Amount")
    String Amount;
    @SerializedName("Service")
    String Service;
    @SerializedName("Info")
    String Info;
    @SerializedName("Instruction")
    String instruction;


    @SerializedName("RealReceiverName")
    private String realReceiverName;

    private String realReceiverIDNumber;
    private String DeliveryType;
    private String userDelivery;
    private String currentPaymentType;
    private boolean selected;
    //endregion
    @SerializedName("IsCOD")
    String isCOD;
    @SerializedName("DeliveryDate")
    private String deliveryDate;
    @SerializedName("DeliveryTime")
    private String deliveryTime;
    @SerializedName("CollectAmount")
    private String collectAmount;
    @SerializedName("ReceiveCollectFee")
    private String receiveCollectFee;
    @SerializedName("ReceiverIDNumber")
    private String receiverIDNumber;
    @SerializedName("DeliveryPOCode")
    private String deliveryPOCode;

    @SerializedName("StatusName")
    private String statusName;
    @SerializedName("ReasonName")
    private String reasonName;
    @SerializedName("SolutionName")
    private String solutionName;
    @SerializedName("CheckStatus")
    private boolean checkStatus;
    @SerializedName("CheckStatusNo")
    private boolean checkStatusNo;
    @SerializedName("Status")
    private String status;

    @SerializedName("ListStatus")
    private ArrayList<StatusInfo> statusInfoArrayList;

    @SerializedName("Note")
    private String note;

    public String getSignatureCapture() {
        return signatureCapture;
    }

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

    public String getInstruction() {
        return instruction;
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

    public String getSenderPhone() {
        return senderPhone;
    }

    public String getReceiveCollectFee() {
        return receiveCollectFee;
    }

    public String getReceiverIDNumber() {
        return receiverIDNumber;
    }

    public String getReceiverName() {
        return contactName;
    }

    public String getReceiverAddress() {
        return contactAddress;
    }

    public String getReceiverPhone() {
        return contactPhone;
    }

    public String getDeliveryPOCode() {
        return deliveryPOCode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getStatusName() {
        return statusName;
    }


    public String getReasonName() {
        return reasonName;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public boolean isCheckStatus() {
        return checkStatus;
    }

    public boolean isCheckStatusNo() {
        return checkStatusNo;
    }

    public ArrayList<StatusInfo> getStatusInfoArrayList() {
        return statusInfoArrayList;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public String getNote() {
        return note;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return Amount;
    }

    public String getService() {
        return Service;
    }

    public String getInfo() {
        return Info;
    }
}
