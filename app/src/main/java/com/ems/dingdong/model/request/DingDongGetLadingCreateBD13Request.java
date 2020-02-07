package com.ems.dingdong.model.request;

import com.google.gson.annotations.SerializedName;

public class DingDongGetLadingCreateBD13Request {
    @SerializedName("FromDate")
    private int fromDate;
    @SerializedName("ToDate")
    private int toDate;
    @SerializedName("PODeliveryCode")
    private String poDeliveryCode;
    @SerializedName("RouteDeliveryCode")
    private String routeDeliveryCode;
    @SerializedName("PostmanCode")
    private String postmanCode;
    @SerializedName("MailtripNumber")
    private String mailtripNumber;


    // Getter Methods

    public int getFromDate() {
        return fromDate;
    }

    public int getToDate() {
        return toDate;
    }

    public String getPoDeliveryCode() {
        return poDeliveryCode;
    }

    public String getRouteDeliveryCode() {
        return routeDeliveryCode;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public String getMailtripNumber() {
        return mailtripNumber;
    }

    // Setter Methods

    public void setFromDate(int fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(int toDate) {
        this.toDate = toDate;
    }

    public void setPoDeliveryCode(String poDeliveryCode) {
        this.poDeliveryCode = poDeliveryCode;
    }

    public void setRouteDeliveryCode(String routeDeliveryCode) {
        this.routeDeliveryCode = routeDeliveryCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public void setMailtripNumber(String mailtripNumber) {
        this.mailtripNumber = mailtripNumber;
    }
}
