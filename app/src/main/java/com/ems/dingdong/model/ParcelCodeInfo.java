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
}
