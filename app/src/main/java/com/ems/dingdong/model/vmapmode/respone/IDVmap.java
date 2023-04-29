package com.ems.dingdong.model.vmapmode.respone;

import com.google.gson.annotations.SerializedName;

public class IDVmap {
    @SerializedName("entityType")
    String entityType;
    @SerializedName("id")
    String id;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}