package com.ems.dingdong.eventbus;

public class BaoPhatCallback {
    private  int position;
    int type;

    public BaoPhatCallback(int type, int position) {
        this.type = type;
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }
}
