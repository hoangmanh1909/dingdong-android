package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResult extends SimpleResult{
    @SerializedName("ListValue")
    List<NotificationInfo> notificationInfoList;

    public List<NotificationInfo> getNotificationInfoList() {
        return notificationInfoList;
    }
}
