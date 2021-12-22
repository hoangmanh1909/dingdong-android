package com.ems.dingdong.model;

import com.ems.dingdong.model.request.vietmap.LocationMap;
import com.google.gson.annotations.SerializedName;

public class ResultModelV1 {

    @SerializedName("result")
    private ResultModel result;

    public ResultModel getResult() {
        return result;
    }

    public void setResult(ResultModel result) {
        this.result = result;
    }
}
