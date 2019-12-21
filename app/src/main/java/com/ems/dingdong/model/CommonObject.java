package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CommonObject extends RealmObject {

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
    //region hoantattin
    @SerializedName("TrackingCode")
    String trackingCode;
    @SerializedName("OrderNumber")
    String orderNumber;
    //endregion
    //region common
    @SerializedName("StatusCode")
    String statusCode;
    @SerializedName("AutoCallStatus")
    String autoCallStatus;

    @PrimaryKey
    @SerializedName(value = "Code", alternate = {"MaE", "ParcelCode"})
    String code;

    @SerializedName(value = "ID", alternate = {"Id"})
    String iD;
    @SerializedName(value = "ContactName", alternate = {"ReciverName", "ReceiverName"})
    String receiverName;
    @SerializedName(value = "ContactPhone", alternate = {"ReciverMobile", "ReceiverPhone"})
    String receiverPhone;
    @SerializedName(value = "ContactAddress", alternate = {"ReciverAddress", "ReceiverAddress"})
    String receiverAddress;
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
    @SerializedName("RouteCode")
    String routeCode;


    @SerializedName("RealReceiverName")
    private String realReceiverName;

    private String realReceiverIDNumber;
    private String DeliveryType;
    private String userDelivery;
    private String paymentChanel;
    private String imageDelivery ;
    private boolean selected;
    private boolean local;
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

    @SerializedName("ReasonCode")
    private String reasonCode;
    @SerializedName("SolutionCode")
    private String solutionCode;

    @SerializedName("CheckStatus")
    private boolean checkStatus;
    @SerializedName("CheckStatusNo")
    private boolean checkStatusNo;
    @SerializedName("Status")
    private String status;

    @SerializedName("ListStatus")
    private RealmList<StatusInfo> statusInfoArrayList;

    @SerializedName("Note")
    private String note;

    @SerializedName("ServiceName")
    private String serviceName;
    @SerializedName("IsPaypost")
    private String isPaypost;
    @SerializedName("ShiftId")
    private String shiftId;

    private String dateSearch;
    @SerializedName("Shipments")
    private RealmList<ParcelCodeInfo> listParcelCode;

    public String getAutoCallStatus() {
        return autoCallStatus;
    }

    public List<ParcelCodeInfo> getListParcelCode() {
        return listParcelCode;
    }

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

    public String getReceiverName() {
        return receiverName;
    }

    public String getReciverName() {
        return receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }


    public String getReciverAddress() {
        return receiverAddress;
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

    public void setPaymentChanel(String paymentChanel) {
        this.paymentChanel = paymentChanel;
    }

    public void setCollectAmount(String collectAmount) {
        this.collectAmount = collectAmount;
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

    public String getPaymentChannel() {
        return paymentChanel;
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


    public String getReceiverAddress() {
        return receiverAddress;
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
        ArrayList<StatusInfo> statusInfos = new ArrayList<>();
        if (statusInfoArrayList != null) {
            statusInfos.addAll(statusInfoArrayList);
        }
        return statusInfos;
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

    public String getDateSearch() {
        return dateSearch;
    }

    public void setDateSearch(String dateSearch) {
        this.dateSearch = dateSearch;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setSignatureCapture(String signatureCapture) {
        this.signatureCapture = signatureCapture;
    }

    public void setSaveLocal(boolean islocal) {
        local = islocal;
    }

    public String getIsPaypost() {
        return isPaypost;
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

    public void setNote(String note) {
        this.note = note;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getImageDelivery() {
        return imageDelivery;
    }

    public void setImageDelivery(String imageDelivery) {
        this.imageDelivery = imageDelivery;
    }

    public String getShiftId() {
        return shiftId;
    }
}
