package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.SerializedName;

public class XoaDiaChiModel {
    @SerializedName("Id")
    long Id;
    @SerializedName("ModifydBy")
    long ModifydBy;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public long getModifydBy() {
        return ModifydBy;
    }

    public void setModifydBy(long modifydBy) {
        ModifydBy = modifydBy;
    }
}
