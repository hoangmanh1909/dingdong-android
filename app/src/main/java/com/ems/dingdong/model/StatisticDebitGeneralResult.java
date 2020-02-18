package com.ems.dingdong.model;

import com.ems.dingdong.model.response.StatisticDebitGeneralResponse;
import com.google.gson.annotations.SerializedName;

public class StatisticDebitGeneralResult extends SimpleResult {
    @SerializedName("Value")
    private StatisticDebitGeneralResponse statisticDebitGeneralResponses;

    public StatisticDebitGeneralResponse getStatisticDebitGeneralResponses() {
        return statisticDebitGeneralResponses;
    }
}
