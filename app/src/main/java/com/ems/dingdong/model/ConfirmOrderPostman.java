package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ConfirmOrderPostman {
    @SerializedName("OrderPostmanID")
    String orderPostmanID;
    @SerializedName("EmployeeID")
    String employeeID;
    @SerializedName("StatusCode")
    String statusCode;
    @SerializedName("ConfirmReason")
    String confirmReason;

    public String parcel;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setOrderPostmanID(String orderPostmanID) {
        this.orderPostmanID = orderPostmanID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setConfirmReason(String confirmReason) {
        this.confirmReason = confirmReason;
    }
}
