package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class HomeCollectInfoResult extends SimpleResult {
    public HomeCollectInfo getHomeCollectInfo() {
        return homeCollectInfo;
    }

    public void setHomeCollectInfo(HomeCollectInfo homeCollectInfo) {
        this.homeCollectInfo = homeCollectInfo;
    }

    @SerializedName("Value")
    private HomeCollectInfo homeCollectInfo;

}
