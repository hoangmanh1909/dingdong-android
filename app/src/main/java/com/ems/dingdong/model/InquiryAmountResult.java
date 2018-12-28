package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class InquiryAmountResult extends SimpleResult {
    @SerializedName("Value")
    private String amount;

    public String getAmount() {
        return amount;
    }
}
