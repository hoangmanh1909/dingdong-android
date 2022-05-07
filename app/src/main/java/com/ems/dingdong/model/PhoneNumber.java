package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class PhoneNumber {
    @SerializedName("Phone")
    private String Phone;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
