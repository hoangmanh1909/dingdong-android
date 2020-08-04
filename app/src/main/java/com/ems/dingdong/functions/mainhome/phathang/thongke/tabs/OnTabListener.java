package com.ems.dingdong.functions.mainhome.phathang.thongke.tabs;

/**
 * Interface to handle event between 2 tabs.
 */
public interface OnTabListener {
    /**
     * Event set total count on title tab
     */
    void onQuantityChanged(int quantity, int currentSetTab);

    /**
     * Synchronize nearby tab when search on current tab
     * @param fromDate from create data searched
     * @param toDate to create data searched
     */
    void onSearched(String fromDate, String toDate, int currentTab);
}
