package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class XacNhanTin {
    @SerializedName("ID")
    String iD;
    @SerializedName("OrderPostmanID")
    String orderPostmanID;
    @SerializedName("Count")
    String count;
    @SerializedName(value = "Code",alternate = {"MaE"})
    String code;
    @SerializedName("Description")
    String description;
    @SerializedName("Route")
    String route;
    @SerializedName("Order")
    String order;
    @SerializedName(value = "ContactName",alternate = {"ReciverName"})
    String contactName;
    @SerializedName(value = "ContactPhone", alternate = {"ReciverMobile"})
    String contactPhone;
    @SerializedName(value = "ContactAddress",alternate = {"ReciverAddress"})
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

    public String getiD() {
        return iD;
    }

    public String getRoute() {
        return route;
    }

    public String getOrder() {
        return order;
    }
}
