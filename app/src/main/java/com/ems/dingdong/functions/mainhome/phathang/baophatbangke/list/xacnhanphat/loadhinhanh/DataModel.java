package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.loadhinhanh;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {
    @SerializedName("Success")
    List<String> Success;
    @SerializedName("Error")
    List<String> Error;

    public List<String> getSuccess() {
        return Success;
    }

    public void setSuccess(List<String> success) {
        Success = success;
    }

    public List<String> getError() {
        return Error;
    }

    public void setError(List<String> error) {
        Error = error;
    }
}
