package com.ems.dingdong.eventbus;

public class CustomNoptien {
    int quantity;

    int currentSetTab;

    public CustomNoptien(int quantity, int currentSetTab) {
        this.quantity = quantity;
        this.currentSetTab = currentSetTab;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCurrentSetTab() {
        return currentSetTab;
    }

    public void setCurrentSetTab(int currentSetTab) {
        this.currentSetTab = currentSetTab;
    }
}
