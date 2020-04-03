package com.ems.dingdong.functions.mainhome.phathang.thongke.tabs;

public interface OnTabListener {
    void onQuantityChanged(int quantity, int currentSetTab);

    void onSearched(String fromDate, String toDate, int currentTab);
}
