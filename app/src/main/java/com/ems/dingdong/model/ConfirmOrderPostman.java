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
