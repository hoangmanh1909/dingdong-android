package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class XacNhanTin {
    @SerializedName("Count")
    String count;
    @SerializedName("Code")
    String code;
    @SerializedName("Description")
    String description;
    @SerializedName("ContactName")
    String contactName;
    @SerializedName("ContactPhone")
    String contactPhone;
    @SerializedName("ContactAddress")
    String contactAddress;

    public String getCount() {
        return count;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }
}
