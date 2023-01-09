package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.SerializedName;

public class VmapAddress {
    @SerializedName("place_id")
    String place_id;
    @SerializedName("ndas_code")
    String ndas_code;
    @SerializedName("name")
    String name;
    @SerializedName("formatted_address")
    String formatted_address;

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getNdas_code() {
        return ndas_code;
    }

    public void setNdas_code(String ndas_code) {
        this.ndas_code = ndas_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
