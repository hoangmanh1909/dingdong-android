package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class AddressModel {
    @SerializedName("Id")
    private int Id;
    @SerializedName("RouteId")
    private int RouteId;
    @SerializedName("RouteCode")
    private String RouteCode;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Detail")
    private String Detail;
    @SerializedName("ProvinceId")
    private String ProvinceId;
    @SerializedName("ProvinceName")
    private String ProvinceName;
    @SerializedName("DistrictId")
    private String DistrictId;
    @SerializedName("DistrictName")
    private String DistrictName;
    @SerializedName("WardId")
    private String WardId;
    @SerializedName("WardName")
    private String WardName;
    @SerializedName("VpostCode")
    private String VpostCode;
    @SerializedName("Phone")
    private String Phone;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    public String getRouteCode() {
        return RouteCode;
    }

    public void setRouteCode(String routeCode) {
        RouteCode = routeCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public String getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(String districtId) {
        DistrictId = districtId;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getWardId() {
        return WardId;
    }

    public void setWardId(String wardId) {
        WardId = wardId;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }

    public String getVpostCode() {
        return VpostCode;
    }

    public void setVpostCode(String vpostCode) {
        VpostCode = vpostCode;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
