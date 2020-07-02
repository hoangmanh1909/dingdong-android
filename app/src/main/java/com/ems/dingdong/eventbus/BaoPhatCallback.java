package com.ems.dingdong.eventbus;

public class BaoPhatCallback {
    private int position;
    private int type;
    private String route;
    private String order;

    public BaoPhatCallback(int type, int position) {
        this.type = type;
        this.position = position;
    }

    public BaoPhatCallback(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int getPosition() {
        return position;
    }

    public String getOrder() {
        return order;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
