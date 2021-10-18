package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ParcelCodeInfo extends RealmObject {
    @SerializedName("TrackingCode")
    private String trackingCode;
    @SerializedName("OrderNumber")
    private String orderNumber;
    @SerializedName("ReceiverName")
    private String receiverName;
    @SerializedName("ShipmentID")
    private int shipmentID;
    @SerializedName("Weight")
    private int weight;
    @SerializedName("OrderId")
    private String orderId;
    @SerializedName("OrderPostmanId")
    private String orderPostmanId;
    @SerializedName("OrderCode")
    private String orderCode;
    @SerializedName("ReferenceCode")
    @Expose
    private String ReferenceCode;

    public String getReferenceCode() {
        return ReferenceCode;
    }

    int status;

    public void setShipmentID(int shipmentID) {
        this.shipmentID = shipmentID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getOrderCode() {
        return orderCode;
    }

    private boolean selected;

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getShipmentID() {
        return shipmentID;
    }

    public int getWeight() {
        return weight;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderPostmanId() {
        return orderPostmanId;
    }

    public void setOrderPostmanId(String orderPostmanId) {
        this.orderPostmanId = orderPostmanId;
    }
}
