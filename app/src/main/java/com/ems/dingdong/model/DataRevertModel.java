package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DataRevertModel {
    @SerializedName("cityId")
    private String cityId;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("districtId")
    private String districtId;
    @SerializedName("districtName")
    private String districtName;
    @SerializedName("wardId")
    private String wardId;
    @SerializedName("wardName")
    private String wardName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
}
