package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class CancelTypeBD13Info {
    @SerializedName("Id")
    String Id;
    @SerializedName("Name")
    String Name;

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
}
