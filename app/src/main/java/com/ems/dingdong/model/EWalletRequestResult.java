package com.ems.dingdong.model;

import com.ems.dingdong.model.response.EWalletRequestResponse;
import com.google.gson.annotations.SerializedName;

public class EWalletRequestResult extends SimpleResult {
    @SerializedName("Value")
    EWalletRequestResponse listEWalletResponse;

    public EWalletRequestResponse getListEWalletResponse() {
        return listEWalletResponse;
    }
}
