package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class NotificationRequest {
    @SerializedName("LadingCode")
    String ladingCode;

    @SerializedName("MobileNumber")
    String mobileNumber;

    @SerializedName("Content")
    String content;

    @SerializedName("CustomerMobileNumber")
    String customerMobileNumber;
}
