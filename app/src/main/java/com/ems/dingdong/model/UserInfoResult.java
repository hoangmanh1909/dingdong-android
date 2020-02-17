package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserInfoResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<UserInfo> userInfos;

    public ArrayList<UserInfo> getUserInfos() {
        return userInfos;
    }
}
