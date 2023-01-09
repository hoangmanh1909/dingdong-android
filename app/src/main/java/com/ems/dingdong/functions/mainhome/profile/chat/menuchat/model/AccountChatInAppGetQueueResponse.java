package com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model;

import com.google.gson.annotations.SerializedName;

public class AccountChatInAppGetQueueResponse {
    @SerializedName("jidQueue")
    private String jidQueue;
    @SerializedName("avatarQueue")
    private String avatarQueue;
    @SerializedName("dispName")
    private String dispName;
    @SerializedName("idQueue")
    private String idQueue;
    @SerializedName("typeQueue")
    private String typeQueue;

    public String getJidQueue() {
        return jidQueue;
    }

    public void setJidQueue(String jidQueue) {
        this.jidQueue = jidQueue;
    }

    public String getAvatarQueue() {
        return avatarQueue;
    }

    public void setAvatarQueue(String avatarQueue) {
        this.avatarQueue = avatarQueue;
    }

    public String getDispName() {
        return dispName;
    }

    public void setDispName(String dispName) {
        this.dispName = dispName;
    }

    public String getIdQueue() {
        return idQueue;
    }

    public void setIdQueue(String idQueue) {
        this.idQueue = idQueue;
    }

    public String getTypeQueue() {
        return typeQueue;
    }

    public void setTypeQueue(String typeQueue) {
        this.typeQueue = typeQueue;
    }
}
