package com.ems.dingdong.callback;

public interface PhoneKhiem {
    void onCallTongDai(String phone);

    void onCall(String phone);

    void onCallEdit(String phone,int type);

}
