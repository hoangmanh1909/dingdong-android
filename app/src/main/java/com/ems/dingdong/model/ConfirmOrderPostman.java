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
    @SerializedName("Code")
    String code;
    @SerializedName("Weigh")
    String weigh;
    @SerializedName("AssignDateTime")
    String assignDateTime;
    @SerializedName("SourceChanel")
    String SourceChanel = "DD_ANDROID";

    public String getSourceChanel() {
        return SourceChanel;
    }

    public void setSourceChanel(String sourceChanel) {
        SourceChanel = sourceChanel;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getAssignDateTime() {
        return assignDateTime;
    }

    public void setAssignDateTime(String assignDateTime) {
        this.assignDateTime = assignDateTime;
    }

    public String getWeigh() {
        return weigh;
    }

    public void setWeigh(String weigh) {
        this.weigh = weigh;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
