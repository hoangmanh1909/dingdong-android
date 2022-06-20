package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class SearchOrderPostman {
    @SerializedName("OrderPostmanID")
    String OrderPostmanID;
    @SerializedName("OrderID")
    String OrderID;
    @SerializedName("PostmanID")
    String PostmanID;
    @SerializedName("Status")
    String Status;
    @SerializedName("FromAssignDate")
    String FromAssignDate;
    @SerializedName("ToAssignDate")
    String ToAssignDate;

    public String getOrderPostmanID() {
        return OrderPostmanID;
    }

    public void setOrderPostmanID(String orderPostmanID) {
        OrderPostmanID = orderPostmanID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getPostmanID() {
        return PostmanID;
    }

    public void setPostmanID(String postmanID) {
        PostmanID = postmanID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFromAssignDate() {
        return FromAssignDate;
    }

    public void setFromAssignDate(String fromAssignDate) {
        FromAssignDate = fromAssignDate;
    }

    public String getToAssignDate() {
        return ToAssignDate;
    }

    public void setToAssignDate(String toAssignDate) {
        ToAssignDate = toAssignDate;
    }
}
