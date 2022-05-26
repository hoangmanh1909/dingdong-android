package com.ems.dingdong.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketNotifyRespone {

    @SerializedName("TicketCode")
    @Expose
    private String ticketCode;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("PushTime")
    @Expose
    private String pushTime;
    @SerializedName("IsSeen")
    @Expose
    private String isSeen;
    @SerializedName("Type")
    @Expose
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(String isSeen) {
        this.isSeen = isSeen;
    }
}
