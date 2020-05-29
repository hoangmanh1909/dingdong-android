package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class NotificationCallingObject {
    @SerializedName("number")
    String number;

    @SerializedName("alias")
    String alias;

    @SerializedName("is_online")
    boolean is_online;

    @SerializedName("type")
    String type;

    public String getNumber() {
        return number;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public String getType() {
        return type;
    }
}
