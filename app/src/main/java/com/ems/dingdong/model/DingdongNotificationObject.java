package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DingdongNotificationObject {
    @SerializedName("callId")
    String callId;

    @SerializedName("serial")
    Integer serial;

    @SerializedName("callStatus")
    String callStatus;

    @SerializedName("from")
    NotificationCallingObject from;

    @SerializedName("projectId")
    Integer projectId;

    public String getCallId() {
        return callId;
    }

    public Integer getSerial() {
        return serial;
    }

    public String getCallStatus() {
        return callStatus;
    }

    public NotificationCallingObject getFrom() {
        return from;
    }

    public Integer getProjectId() {
        return projectId;
    }
}
