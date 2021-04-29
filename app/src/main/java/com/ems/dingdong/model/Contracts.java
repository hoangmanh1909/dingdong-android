package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class Contracts {
    @SerializedName("Id")
    public long Id;
    @SerializedName("ContractNumber")
    public String ContractNumber;

    @SerializedName("ProvinceId")
    public int ProvinceId;

    @SerializedName("ProvinceName")
    public String ProvinceName;
    @SerializedName("District")
    public int District;
    @SerializedName("DistrictName")
    public String DistrictName;
    @SerializedName("Ward")
    public int Ward;
    @SerializedName("WardName")
    public String WardName;
    @SerializedName("ContactName")
    public String ContactName;
    @SerializedName("ContactMobileNumber")
    public String ContactMobileNumber;

    @SerializedName("Street")
    public String Street;

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getContractNumber() {
        return ContractNumber;
    }

    public void setContractNumber(String contractNumber) {
        ContractNumber = contractNumber;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    public int getDistrict() {
        return District;
    }

    public void setDistrict(int district) {
        District = district;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public int getWard() {
        return Ward;
    }

    public void setWard(int ward) {
        Ward = ward;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactMobileNumber() {
        return ContactMobileNumber;
    }

    public void setContactMobileNumber(String contactMobileNumber) {
        ContactMobileNumber = contactMobileNumber;
    }
}
