package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DistrictModels {
    /// <summary>
    /// Id huyện
    /// </summary>
    @SerializedName("DistrictId")
    public long DistrictId;

    /// <summary>
    /// Tên huyện
    /// </summary>
    @SerializedName("DistrictName")
    public String DistrictName ;

    public long getDistrictId() {
        return DistrictId;
    }

    public void setDistrictId(long districtId) {
        DistrictId = districtId;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }
}
