package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class LadingRefundTotalRespone {
    @SerializedName("IsRefund")
    String IsRefund;
    @SerializedName("Quantity")
    int Quantity;

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getIsRefund() {
        return IsRefund;
    }

    public void setIsRefund(String isRefund) {
        IsRefund = isRefund;
    }
}
