package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class BalanceModel extends BaseRequestModel {

    @SerializedName("FromDate")
    private int FromDate;
    @SerializedName("ToDate")
    private int ToDate;

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
}
