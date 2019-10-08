package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ConfirmAllOrderPostmanResult extends SimpleResult {
    @SerializedName("Value")
    private ConfirmAllOrderPostman allOrderPostman;

    public ConfirmAllOrderPostman getAllOrderPostman() {
        return allOrderPostman;
    }
}
