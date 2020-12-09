package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class AutoCallVHT {
    @SerializedName("lading_code")
    private String ladingCode;
    @SerializedName("po_province_code")
    private String poProvinceCode;
    @SerializedName("po_district_code")
    private String poDistrictCode;
    @SerializedName("po_code")
    private String poCode;
    @SerializedName("route_code")
    private String routeCode;
    @SerializedName("postman_code")
    private String postmanCode;
    @SerializedName("amount")
    private String amount;
    @SerializedName("descripton")
    private String descripton;
    @SerializedName("po_delivery_name")
    private String poDeliveryName;
    @SerializedName("po_delivery_address")
    private String poDeliveryAddress;
    @SerializedName("po_delivery_tel")
    private String poDeliveryTel;
    @SerializedName("receiver_tel")
    private String receiverTel;

    public AutoCallVHT(String ladingCode, String poProvinceCode, String poDistrictCode, String poCode, String routeCode, String postmanCode, String amount, String descripton, String poDeliveryName, String poDeliveryAddress, String poDeliveryTel, String receiverTel) {
        this.ladingCode = ladingCode;
        this.poProvinceCode = poProvinceCode;
        this.poDistrictCode = poDistrictCode;
        this.poCode = poCode;
        this.routeCode = routeCode;
        this.postmanCode = postmanCode;
        this.amount = amount;
        this.descripton = descripton;
        this.poDeliveryName = poDeliveryName;
        this.poDeliveryAddress = poDeliveryAddress;
        this.poDeliveryTel = poDeliveryTel;
        this.receiverTel = receiverTel;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public String getPoProvinceCode() {
        return poProvinceCode;
    }

    public String getPoDistrictCode() {
        return poDistrictCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescripton() {
        return descripton;
    }

    public String getPoDeliveryName() {
        return poDeliveryName;
    }

    public String getPoDeliveryAddress() {
        return poDeliveryAddress;
    }

    public String getPoDeliveryTel() {
        return poDeliveryTel;
    }

    public String getReceiverTel() {
        return receiverTel;
    }
}
