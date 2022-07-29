package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TabLogCallRespone {
    @SerializedName("PostmanCode")
    private String PostmanCode;
    @SerializedName("PostmanName")
    private String PostmanName;
    @SerializedName("OutBound")
    private OutBound OutBound;
    @SerializedName("InBound")
    private OutBound InBound;
    @SerializedName("POCode")
    private String POCode;
    @SerializedName("FromDate")
    private String FromDate;
    @SerializedName("ToDate")
    private String ToDate;
    @SerializedName("PostmanTel")
    private String PostmanTel;

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

    public String getPostmanName() {
        return PostmanName;
    }

    public void setPostmanName(String postmanName) {
        PostmanName = postmanName;
    }

    public com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.OutBound getOutBound() {
        return OutBound;
    }

    public void setOutBound(com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.OutBound outBound) {
        OutBound = outBound;
    }

    public com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.OutBound getInBound() {
        return InBound;
    }

    public void setInBound(com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data.OutBound inBound) {
        InBound = inBound;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }
}
