package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.xacnhanphat.parital;

import com.google.gson.annotations.SerializedName;

public class ThuPhiMode {
    @SerializedName("IsCancelOrder ")
    private boolean isCancelOrder ;
    @SerializedName("FeeCancelOrder")
    private long FeeCancelOrder;

    public boolean isCancelOrder() {
        return isCancelOrder;
    }

    public void setCancelOrder(boolean cancelOrder) {
        isCancelOrder = cancelOrder;
    }

    public long getFeeCancelOrder() {
        return FeeCancelOrder;
    }

    public void setFeeCancelOrder(long feeCancelOrder) {
        FeeCancelOrder = feeCancelOrder;
    }
}
