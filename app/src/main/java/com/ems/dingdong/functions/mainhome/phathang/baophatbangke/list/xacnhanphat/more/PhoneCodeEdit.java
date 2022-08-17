package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.more;

import com.google.gson.annotations.SerializedName;

public class PhoneCodeEdit {
    @SerializedName("id")
    private String id;
    @SerializedName("phoneNumber")
    private String phoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
