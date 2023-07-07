package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
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
    @SerializedName("ServiceNameMPITS")
    String ServiceNameMPITS;
    @SerializedName("ServiceCodeMPITS")
    String ServiceCodeMPITS;
    @SerializedName("QuotasTimeFirst")
    @Expose
    private String QuotasTimeFirst;
    @SerializedName("QuotasTimeSuccess")
    @Expose
    private String QuotasTimeSuccess;
    @SerializedName("SourceChanel")
    String SourceChanel = "DD_ANDROID";

    public String getQuotasTimeFirst() {
        return QuotasTimeFirst;
    }

    public void setQuotasTimeFirst(String quotasTimeFirst) {
        QuotasTimeFirst = quotasTimeFirst;
    }

    public String getQuotasTimeSuccess() {
        return QuotasTimeSuccess;
    }

    public void setQuotasTimeSuccess(String quotasTimeSuccess) {
        QuotasTimeSuccess = quotasTimeSuccess;
    }

    public String getServiceNameMPITS() {
        return ServiceNameMPITS;
    }

    public void setServiceNameMPITS(String serviceNameMPITS) {
        ServiceNameMPITS = serviceNameMPITS;
    }

    public String getServiceCodeMPITS() {
        return ServiceCodeMPITS;
    }

    public void setServiceCodeMPITS(String serviceCodeMPITS) {
        ServiceCodeMPITS = serviceCodeMPITS;
    }

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
