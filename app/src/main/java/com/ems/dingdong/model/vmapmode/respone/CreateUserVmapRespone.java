package com.ems.dingdong.model.vmapmode.respone;

import com.google.gson.annotations.SerializedName;

public class CreateUserVmapRespone {
    @SerializedName("id")
    IDVmap idVmap;
    @SerializedName("createdTime")
    String createdTime;

    public IDVmap getId() {
        return idVmap;
    }

    public void setId(IDVmap id) {
        this.idVmap = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}



