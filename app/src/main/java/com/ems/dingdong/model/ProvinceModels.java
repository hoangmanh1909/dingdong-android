package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ProvinceModels {

    /// Tỉnh - thành phố
    /// Id tỉnh
    @SerializedName("ProvinceId")
    public long ProvinceId;
    /// <summary>
    /// Tên tỉnh
    /// </summary>
    @SerializedName("ProvinceName")
    public String ProvinceName;

    public long getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(long provinceId) {
        ProvinceId = provinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }
}
