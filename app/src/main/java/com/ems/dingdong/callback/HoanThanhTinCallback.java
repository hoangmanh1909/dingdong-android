package com.ems.dingdong.callback;

import com.ems.dingdong.model.ReasonInfo;

public interface HoanThanhTinCallback {
    void onResponse(String statusCode, String quantity, ReasonInfo reasonInfo, String pickUpDate, String pickUpTime);
}
