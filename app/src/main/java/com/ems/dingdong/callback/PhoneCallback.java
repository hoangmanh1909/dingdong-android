package com.ems.dingdong.callback;

public interface PhoneCallback {
    void onCallResponse(String phone, int callType);
    void onUpdateResponse(String phone);
}
