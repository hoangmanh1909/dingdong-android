package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.Ignore;

public class HoanTatTinRequest {
    @SerializedName("EmployeeID")
    String employeeID;
    @SerializedName("OrderID")
    long orderID;
    @SerializedName("OrderPostmanID")
    long orderPostmanID;
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
    //    @SerializedName("ShipmentCode")
//    List<String> shipmentCode;
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

    @SerializedName("NoteReason")
    String NoteReason;
    @SerializedName("ShipmentCode")
    String shipmentCodev1;

    public String getShipmentCodev1() {
        return shipmentCodev1;
    }

    public void setShipmentCodev1(String shipmentCodev1) {
        this.shipmentCodev1 = shipmentCodev1;
    }

    @SerializedName("CollectLat")
    private String CollectLat;
    @SerializedName("CollectLon")
    private String CollectLon;
    @SerializedName("SenderLat")
    private double SenderLat;
    @SerializedName("SenderLon")
    private double SenderLon;
    @SerializedName("POCollectLat")
    private String POCollectLat;
    @SerializedName("POCollectLon")
    private String POCollectLon;
    @SerializedName("OrderCode")
    private String orderCode;
    @SerializedName("SourceChanel")
    private String SourceChanel;

    public String getSourceChanel() {
        return SourceChanel;
    }

    public void setSourceChanel(String sourceChanel) {
        SourceChanel = sourceChanel;
    }

    @Ignore
    List<String> shipmentCodeV1List;
    @Ignore
    List<Integer> shipmentIdsV1List;

    public List<Integer> getShipmentIdsV1() {
        return shipmentIdsV1List;
    }

    public void setShipmentIdsV1(List<Integer> shipmentIdsV1) {
        shipmentIdsV1List = shipmentIdsV1;
    }

    public List<String> getShipmentCodeV1List() {
        return shipmentCodeV1List;
    }

    public void setShipmentCodeV1List(List<String> shipmentCodeV1List) {
        this.shipmentCodeV1List = shipmentCodeV1List;
    }

    public List<Integer> getShipmentIdsV1List() {
        return shipmentIdsV1List;
    }

    public void setShipmentIdsV1List(List<Integer> shipmentIdsV1List) {
        this.shipmentIdsV1List = shipmentIdsV1List;
    }

    public void addShipmentCodeV1(String code) {
        if (shipmentCodeV1List == null) {
            shipmentCodeV1List = new ArrayList<>();
        }
        shipmentCodeV1List.add(code);
    }

    public void addShipmentIdsV1(Integer code) {
        if (shipmentIdsV1List == null) {
            shipmentIdsV1List = new ArrayList<>();
        }
        shipmentIdsV1List.add(code);
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getOrderPostmanID() {
        return orderPostmanID;
    }

    public void setOrderPostmanID(long orderPostmanID) {
        this.orderPostmanID = orderPostmanID;
    }

//    public void setShipmentCode(List<String> shipmentCode) {
//        this.shipmentCode = shipmentCode;
//    }

    public String getEmployeeID() {
        return employeeID;
    }


    public String getQuantity() {
        return quantity;
    }

    public String getCollectReason() {
        return collectReason;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public String getFile() {
        return file;
    }


    public String getReasonCode() {
        return reasonCode;
    }

    public List<Integer> getShipmentIds() {
        return shipmentIds;
    }

    public String getConfirmContent() {
        return confirmContent;
    }

    public String getConfirmQuantity() {
        return confirmQuantity;
    }

    public String getConfirmSignature() {
        return confirmSignature;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCollectLat() {
        return CollectLat;
    }

    public void setCollectLat(String collectLat) {
        CollectLat = collectLat;
    }

    public String getCollectLon() {
        return CollectLon;
    }

    public void setCollectLon(String collectLon) {
        CollectLon = collectLon;
    }

    public double getSenderLat() {
        return SenderLat;
    }

    public void setSenderLat(double senderLat) {
        SenderLat = senderLat;
    }

    public double getSenderLon() {
        return SenderLon;
    }

    public void setSenderLon(double senderLon) {
        SenderLon = senderLon;
    }

    public String getPOCollectLat() {
        return POCollectLat;
    }

    public void setPOCollectLat(String POCollectLat) {
        this.POCollectLat = POCollectLat;
    }

    public String getPOCollectLon() {
        return POCollectLon;
    }

    public void setPOCollectLon(String POCollectLon) {
        this.POCollectLon = POCollectLon;
    }

    public String getNoteReason() {
        return NoteReason;
    }

    public void setNoteReason(String noteReason) {
        NoteReason = noteReason;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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

    public String getStatusCode() {
        return statusCode;
    }
}
