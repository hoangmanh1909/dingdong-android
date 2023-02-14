package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class STTTicketManagementTotalRespone {
    @SerializedName("StatusCode")
    private String StatusCode;
    @SerializedName("StatusName")
    private String StatusName;
    @SerializedName("TicketCode")
    private String TicketCode;
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("CreatedDate")
    private String CreatedDate;
    @SerializedName("Quantity")
    private int Quantity;

    public String getTicketCode() {
        return TicketCode;
    }

    public void setTicketCode(String ticketCode) {
        TicketCode = ticketCode;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
