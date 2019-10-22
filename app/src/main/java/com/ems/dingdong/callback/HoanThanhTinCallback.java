package com.ems.dingdong.callback;

import com.ems.dingdong.model.ReasonInfo;

import java.util.ArrayList;

public interface HoanThanhTinCallback {
    void onResponse(String statusCode, ReasonInfo reasonInfo, String pickUpDate, String pickUpTime, ArrayList<Integer> array);//String quantity,
}
