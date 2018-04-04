package com.vinatti.dingdong.model;

public class HomeInfo {
    int resId;
    String info;
    String name;

    public HomeInfo(int resId, String info, String name) {
        this.resId = resId;
        this.info = info;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }
}
