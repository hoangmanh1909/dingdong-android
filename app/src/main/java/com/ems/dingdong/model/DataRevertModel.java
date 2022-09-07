package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DataRevertModel {
    @SerializedName("cityId")
    private Integer cityId;
    @SerializedName("cityName")
    private String cityName;
    @SerializedName("districtId")
    private Integer districtId;
    @SerializedName("districtName")
    private String districtName;
    @SerializedName("wardId")
    private Integer wardId;
    @SerializedName("wardName")
    private String wardName;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Integer getWardId() {
        return wardId;
    }

    public void setWardId(Integer wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
}
