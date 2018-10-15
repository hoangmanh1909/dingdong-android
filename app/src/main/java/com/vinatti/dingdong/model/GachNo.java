package com.vinatti.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GachNo {
    @SerializedName("LadingCode")
    @Expose
    private String ladingCode;
    @SerializedName("StatusDate")
    @Expose
    private String statusDate;
    @SerializedName("StatusTime")
    @Expose
    private String statusTime;
    @SerializedName("PoCode")
    @Expose
    private String poCode;
    @SerializedName("PoName")
    @Expose
    private String poName;
    @SerializedName("RouteCode")
    @Expose
    private String routeCode;
    @SerializedName("RouteName")
    @Expose
    private String routeName;
    @SerializedName("PostmanName")
    @Expose
    private String postmanName;
    @SerializedName("StatusName")
    @Expose
    private String statusName;
    @SerializedName("ReceiverName")
    @Expose
    private String receiverName;
    @SerializedName("ReceiverPID")
    @Expose
    private String receiverPID;
    @SerializedName("CollectAmount")
    @Expose
    private String collectAmount;
    @SerializedName("IsPaypost")
    @Expose
    private String isPaypost;

    private boolean isSelected;

    public String getLadingCode() {
        return ladingCode;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public String getPoCode() {
        return poCode;
    }

    public String getPoName() {
        return poName;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getPostmanName() {
        return postmanName;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getCollectAmount() {
        return collectAmount;
    }

    public String getIsPaypost() {
        return isPaypost;
    }

    public String getReceiverPID() {
        return receiverPID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
