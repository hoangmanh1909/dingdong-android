package com.ems.dingdong.model.vmapmode;

import com.google.gson.annotations.SerializedName;

public class CreateUserVmap {
    @SerializedName("name")
    String name;
    @SerializedName("type")
    String type;
    @SerializedName("label")
    String label;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
