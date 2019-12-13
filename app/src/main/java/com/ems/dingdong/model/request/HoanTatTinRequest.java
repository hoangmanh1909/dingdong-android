package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HoanTatTinRequest {
    @SerializedName("EmployeeID")
    String employeeID;
    @SerializedName("OrderID")
    String orderID;
    @SerializedName("OrderPostmanID")
    String orderPostmanID;
    @SerializedName("StatusCode")
    String statusCode;
    @SerializedName("Quantity")
    String quantity;
    @SerializedName("CollectReason")
    String collectReason;
    @SerializedName("PickUpDate")
    String pickUpDate;
    @SerializedName("PickUpTime")
    String pickUpTime;
    @SerializedName("OrderImage")
    String file;
    @SerializedName("ShipmentCode")
    String scan;
    @SerializedName("ReasonCode")
    String reasonCode;
    @SerializedName("ShipmentIds")
    List<Integer> shipmentIds;
    @SerializedName("ConfirmContent")
    String confirmContent;
    @SerializedName("ConfirmQuantity")
    String confirmQuantity;
    @SerializedName("ConfirmSignature")
    String confirmSignature;

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setOrderPostmanID(String orderPostmanID) {
        this.orderPostmanID = orderPostmanID;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setCollectReason(String collectReason) {
        this.collectReason = collectReason;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public void setShipmentIds(List<Integer> shipmentIds) {
        this.shipmentIds = shipmentIds;
    }

    public void setConfirmContent(String confirmContent) {
        this.confirmContent = confirmContent;
    }

    public void setConfirmQuantity(String confirmQuantity) {
        this.confirmQuantity = confirmQuantity;
    }

    public void setConfirmSignature(String confirmSignature) {
        this.confirmSignature = confirmSignature;
    }
}
