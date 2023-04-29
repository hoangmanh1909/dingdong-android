package com.ems.dingdong.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatMode {
    @SerializedName("chat_id")
    @Expose
    private String chat_id = "-1001737924095";

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("disable_notification")
    boolean disable_notification = true;

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDisable_notification() {
        return disable_notification;
    }

    public void setDisable_notification(boolean disable_notification) {
        this.disable_notification = disable_notification;
    }
}
