package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.SerializedName;

public class IDStresst {
    @SerializedName("ProvinceId")
    int ProvinceId;
    @SerializedName("DistrictId")
    int DistrictId;
    @SerializedName("WardId")
    int WardId;

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }

    public int getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(int districtId) {
        DistrictId = districtId;
    }

    public int getWardId() {
        return WardId;
    }

    public void setWardId(int wardId) {
        WardId = wardId;
    }
}
