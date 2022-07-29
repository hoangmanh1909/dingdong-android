package com.ems.dingdong.functions.mainhome.phathang.logcuocgoi.tablogcall.data;

import com.google.gson.annotations.SerializedName;

public class OutBound {
    @SerializedName("Total")
    private String Total;
    @SerializedName("Success")
    private String Success;
    @SerializedName("Error")
    private String Error;

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
