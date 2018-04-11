package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class XacNhanTin {
    @SerializedName("OrderPostmanID")
    String orderPostmanID;
    @SerializedName("Count")
    String count;
    @SerializedName("Code")
    String code;
    @SerializedName("Description")
    String description;
    @SerializedName("ContactName")
    String contactName;
    @SerializedName("ContactPhone")
    String contactPhone;
    @SerializedName("ContactAddress")
    String contactAddress;
    @SerializedName("AssignDateTime")
    String assignDateTime;
    @SerializedName("AssignFullName")
    String assignFullName;
    @SerializedName("Quantity")
    String quantity;
    @SerializedName("Weigh")
    String weigh;
    @SerializedName("StatusCode")
    String statusCode;

    public String getCount() {
        return count;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public String getOrderPostmanID() {
        return orderPostmanID;
    }

    public String getAssignDateTime() {
        return assignDateTime;
    }

    public String getAssignFullName() {
        return assignFullName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeigh() {
        return weigh;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
