package com.ems.dingdong.model;

import com.ems.dingdong.model.response.CancelStatisticItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CancelDeliveryResult extends SimpleResult{
    @SerializedName("ListValue")
    List<CancelStatisticItem> statisticItemList;

    public List<CancelStatisticItem> getStatisticItemList() {
        return statisticItemList;
    }
}
