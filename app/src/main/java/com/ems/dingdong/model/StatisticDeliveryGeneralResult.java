package com.ems.dingdong.model;

import com.ems.dingdong.model.response.StatisticDeliveryGeneralResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StatisticDeliveryGeneralResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<StatisticDeliveryGeneralResponse> statisticDeliveryGeneralResponses;

    public ArrayList<StatisticDeliveryGeneralResponse> getStatisticDeliveryGeneralResponses() {
        return statisticDeliveryGeneralResponses;
    }
}
