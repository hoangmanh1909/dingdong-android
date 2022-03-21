package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderChangeRouteModel {
    @SerializedName("OrderChangeRouteId")
    @Expose
    private int orderChangeRouteId;
    @SerializedName("OrderCode")
    @Expose
    private String orderCode;
    @SerializedName("DivideDate")
    @Expose
    private String divideDate;
    @SerializedName("DivideByName")
    @Expose
    private String divideByName;
    @SerializedName("Contents")
    @Expose
    private String contents;
    @SerializedName("Quantity")
    @Expose
    private int quantity;
    @SerializedName("Weight")
    @Expose
    private int weight;
    @SerializedName("ContactAddress")
    @Expose
    private String contactAddress;
    @SerializedName("ContactName")
    @Expose
    private String contactName;
    @SerializedName("ContactPhone")
    @Expose
    private String contactPhone;
    @SerializedName("StatusName")
    @Expose
    private String statusName;
    @SerializedName("ModifyDate")
    @Expose
    private String modifyDate;
    @SerializedName("IsCancel")
    @Expose
    private boolean isCancel;
    @SerializedName("IsReject")
    @Expose
    private boolean isReject;
    @SerializedName("IsAccept")
    @Expose
    private boolean isAccept;
    @SerializedName("RouteCode")
    @Expose
    private String routeCode;
    @SerializedName("RouteName")
    @Expose
    private String routeName;
    @SerializedName("PostmanCode")
    @Expose
    private String postmanCode;
    @SerializedName("PostmanName")
    @Expose
    private String postmanName;
  @SerializedName("AssignFullName")
    @Expose
    private String AssignFullName;
@SerializedName("CustomerName")
    @Expose
    private String CustomerName;

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getAssignFullName() {
        return AssignFullName;
    }

    public void setAssignFullName(String assignFullName) {
        AssignFullName = assignFullName;
    }

    public int getOrderChangeRouteId() {
        return orderChangeRouteId;
    }

    public void setOrderChangeRouteId(int orderChangeRouteId) {
        this.orderChangeRouteId = orderChangeRouteId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDivideDate() {
        return divideDate;
    }

    public void setDivideDate(String divideDate) {
        this.divideDate = divideDate;
    }

    public String getDivideByName() {
        return divideByName;
    }

    public void setDivideByName(String divideByName) {
        this.divideByName = divideByName;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public boolean getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    public boolean getIsReject() {
        return isReject;
    }

    public void setIsReject(boolean isReject) {
        this.isReject = isReject;
    }

    public boolean getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(boolean isAccept) {
        this.isAccept = isAccept;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public String getPostmanName() {
        return postmanName;
    }

    public void setPostmanName(String postmanName) {
        this.postmanName = postmanName;
    }
}
