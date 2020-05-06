package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class NotificationInfo {

    @SerializedName("NotifyTile")
    String title;
    @SerializedName("NotifyDes")
    String content;
    @SerializedName("ID")
    Integer id;
    @SerializedName("MobileNumber")
    String mobileNumber;
    @SerializedName("NotifyType")
    Integer notifyType;
    @SerializedName("CreatedDate")
    String createDate;
    @SerializedName("Navigation")
    Integer navigation;
    @SerializedName("AddInfo")
    String addInfo;
    @SerializedName("IsRead")
    String isRead;
    @SerializedName("IsPush")
    String isPush;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public Integer getNotifyType() {
        return notifyType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Integer getNavigation() {
        return navigation;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public String getIsRead() {
        return isRead;
    }

    public String getIsPush() {
        return isPush;
    }
}
