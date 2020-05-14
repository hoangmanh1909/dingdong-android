package com.ems.dingdong.model;

import com.ems.dingdong.model.response.LinkEWalletResponse;
import com.google.gson.annotations.SerializedName;

public class LinkEWalletResult extends SimplePaypostResult {

    @SerializedName("data")
    LinkEWalletResponse response;

    public LinkEWalletResponse getResponse() {
        return response;
    }
}
