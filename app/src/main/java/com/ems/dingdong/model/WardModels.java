package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class WardModels {
    /// <summary>
    /// Id huyện
    /// </summary>
    @SerializedName("WardId")
    public long wardsId;

    /// <summary>
    /// Tên huyện
    /// </summary>
    @SerializedName("WardName")
    public String WardsName ;

    public long getWardsId() {
        return wardsId;
    }

    public void setWardsId(long wardsId) {
        this.wardsId = wardsId;
    }

    public String getWardsName() {
        return WardsName;
    }

    public void setWardsName(String wardsName) {
        WardsName = wardsName;
    }
}
