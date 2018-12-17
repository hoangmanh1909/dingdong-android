package com.ems.dingdong.model;

public class HomeInfo {
    private int id;
    int resId;
    String info;
    String name;

    public HomeInfo(int resId, String info, String name) {
        this.resId = resId;
        this.info = info;
        this.name = name;
    }

    public HomeInfo(int id, int resId, String name) {
        this.id = id;
        this.resId = resId;
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


    public int getId() {
        return id;
    }

}
