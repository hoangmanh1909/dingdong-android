package com.ems.dingdong.model;

import com.ems.dingdong.model.response.StatisticDeliveryDetailResponse;
import com.ems.dingdong.model.response.StatisticDeliveryGeneralResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StatisticDeliveryDetailResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<StatisticDeliveryDetailResponse> statisticDeliveryDetailResponses;

    public ArrayList<StatisticDeliveryDetailResponse> getStatisticDeliveryDetailResponses() {
        return statisticDeliveryDetailResponses;
    }
}
