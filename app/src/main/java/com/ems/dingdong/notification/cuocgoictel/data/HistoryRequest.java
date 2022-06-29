package com.ems.dingdong.notification.cuocgoictel.data;

import com.ems.dingdong.model.BaseRequestModel;
import com.google.gson.annotations.SerializedName;

public class HistoryRequest {
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("POCode")
    private String POCode;
    @SerializedName("FromDate")
    private int FromDate;
    @SerializedName("ToDate")
    private int ToDate;
    @SerializedName("PostmanTel")
    private String PostmanTel;
    @SerializedName("PostmanCode ")
    private String PostmanCode;

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public int getFromDate() {
        return FromDate;
    }

    public void setFromDate(int fromDate) {
        FromDate = fromDate;
    }

    public int getToDate() {
        return ToDate;
    }

    public void setToDate(int toDate) {
        ToDate = toDate;
    }

    public String getPostmanTel() {
        return PostmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        PostmanTel = postmanTel;
    }

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }
}
