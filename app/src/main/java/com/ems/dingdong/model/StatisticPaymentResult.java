package com.ems.dingdong.model;

import com.ems.dingdong.model.response.StatisticPaymentResponse;
import com.google.gson.annotations.SerializedName;

public class StatisticPaymentResult extends SimpleResult {
    @SerializedName("Value")
    private StatisticPaymentResponse statisticPaymentResponses;

    public StatisticPaymentResponse getStatisticPaymentResponses() {
        return statisticPaymentResponses;
    }
}
