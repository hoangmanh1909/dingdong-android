package com.ems.dingdong.callback;

public interface RouteManagerDialogCallback {
    void onResponse(String fromDate, String toDate, String ladingCode, String statusCode, Integer fromRouteId);
}
