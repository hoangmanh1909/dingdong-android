package com.ems.dingdong.callback;

public interface HoanThanhTinCallback {
    void onResponse(String statusCode, String quantity, String collectReason, String pickUpDate, String pickUpTime);
}
