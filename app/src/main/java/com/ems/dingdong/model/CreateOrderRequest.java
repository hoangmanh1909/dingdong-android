package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateOrderRequest {
    @SerializedName("PostmanId")
    public String PostmanId;
    @SerializedName("RouteId")
    public String RouteId;
    @SerializedName("POCode")
    public String POCode;
    /// <summary>
    /// Mã khách hàng
    /// </summary>
    @SerializedName("CustomerCode")
    public String CustomeCode;
    @SerializedName("CustomeName")
    public String CustomeName;
    @SerializedName("CustomeEmail")
    public String CustomeEmail;
    @SerializedName("CustomePhone")
    public String CustomePhone;
    @SerializedName("Contents")
    public String Contents;
    @SerializedName("Quantity")
    public String Quantity;
    @SerializedName("Weight")
    public String Weight;

    /// <summary>
    /// Ngày yêu cầu YYYYMMDD
    /// </summary>
    @SerializedName("PickupDate")
    public String PickupDate;
    /// <summary>
    /// Giờ yêu cầu HH24MI
    /// </summary>
    @SerializedName("PickupHours")
    public int PickupHours;
    /// <summary>
    /// Id tỉnh lấy hàng
    /// </summary>
    @SerializedName("PickupProvinceId")
    public int PickupProvinceId;
    @SerializedName("PickupDistrictId")
    public int PickupDistrictId;
    @SerializedName("PickupWardId")
    public int PickupWardId;
    @SerializedName("PickupStreet")
    public String PickupStreet;
    @SerializedName("ContactName")
    public String ContactName;
    @SerializedName("ContactPhone")
    public String ContactPhone;

    public String getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(String postmanId) {
        PostmanId = postmanId;
    }

    public String getRouteId() {
        return RouteId;
    }

    public void setRouteId(String routeId) {
        RouteId = routeId;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getCustomeCode() {
        return CustomeCode;
    }

    public void setCustomeCode(String customeCode) {
        CustomeCode = customeCode;
    }

    public String getCustomeName() {
        return CustomeName;
    }

    public void setCustomeName(String customeName) {
        CustomeName = customeName;
    }

    public String getCustomeEmail() {
        return CustomeEmail;
    }

    public void setCustomeEmail(String customeEmail) {
        CustomeEmail = customeEmail;
    }

    public String getCustomePhone() {
        return CustomePhone;
    }

    public void setCustomePhone(String customePhone) {
        CustomePhone = customePhone;
    }

    public String getContents() {
        return Contents;
    }

    public void setContents(String contents) {
        Contents = contents;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getPickupDate() {
        return PickupDate;
    }

    public void setPickupDate(String pickupDate) {
        PickupDate = pickupDate;
    }

    public int getPickupHours() {
        return PickupHours;
    }

    public void setPickupHours(int pickupHours) {
        PickupHours = pickupHours;
    }

    public int getPickupProvinceId() {
        return PickupProvinceId;
    }

    public void setPickupProvinceId(int pickupProvinceId) {
        PickupProvinceId = pickupProvinceId;
    }

    public int getPickupDistrictId() {
        return PickupDistrictId;
    }

    public void setPickupDistrictId(int pickupDistrictId) {
        PickupDistrictId = pickupDistrictId;
    }

    public int getPickupWardId() {
        return PickupWardId;
    }

    public void setPickupWardId(int pickupWardId) {
        PickupWardId = pickupWardId;
    }

    public String getPickupStreet() {
        return PickupStreet;
    }

    public void setPickupStreet(String pickupStreet) {
        PickupStreet = pickupStreet;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String contactPhone) {
        ContactPhone = contactPhone;
    }
}
