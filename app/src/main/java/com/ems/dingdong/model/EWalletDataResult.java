package com.ems.dingdong.model;

import com.ems.dingdong.model.response.EWalletDataResponse;
import com.ems.dingdong.model.response.EWalletRequestResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EWalletDataResult extends SimpleResult {
    @SerializedName("Value")
    List<EWalletDataResponse> listEWalletData;

    public List<EWalletDataResponse> getListEWalletData() {
        return listEWalletData;
    }

}
