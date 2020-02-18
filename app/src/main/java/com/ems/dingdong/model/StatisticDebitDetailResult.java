package com.ems.dingdong.model;

import com.ems.dingdong.model.response.StatisticDebitDetailResponse;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class StatisticDebitDetailResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<StatisticDebitDetailResponse> statisticDebitDetailResponses;

    public ArrayList<StatisticDebitDetailResponse> getStatisticDebitDetailResponses() {
        return statisticDebitDetailResponses;
    }
}
