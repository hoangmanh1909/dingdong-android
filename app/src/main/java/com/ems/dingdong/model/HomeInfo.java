package com.ems.dingdong.model;

public class HomeInfo {
    private int id;
    int resId;
    String name;


    public HomeInfo(int id, int resId, String name) {
        this.id = id;
        this.resId = resId;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }


    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }

}
