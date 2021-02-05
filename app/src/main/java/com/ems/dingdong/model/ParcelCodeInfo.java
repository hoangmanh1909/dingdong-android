package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class ParcelCodeInfo extends RealmObject {
    @SerializedName("TrackingCode")
    private String parcelCode;
    @SerializedName("OrderNumber")
    private String orderNumber;
    @SerializedName("ReceiverName")
    private String receiverName;
    @SerializedName("ShipmentID")
    private int shipmentID;
    @SerializedName("Weight")
    private int weight;
    @SerializedName("OrderId")
    private long orderId;
    @SerializedName("OrderPostmanId")
    private long orderPostmanId;

    private boolean selected;
    public String getParcelCode() {
        return parcelCode;
    }

    public void setParcelCode(String parcelCode) {
        this.parcelCode = parcelCode;
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getOrderPostmanId() {
        return orderPostmanId;
    }

    public void setOrderPostmanId(long orderPostmanId) {
        this.orderPostmanId = orderPostmanId;
    }
}
