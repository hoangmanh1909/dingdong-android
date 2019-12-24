package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StatisticCollectResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<StatisticCollect> statisticCollects;

    public ArrayList<StatisticCollect> getStatisticCollects() {
        return statisticCollects;
    }
}
