package com.ems.dingdong.callback;

public interface PhoneCallback {
    void onCallSenderResponse(String phone);

    void onCallReceiverResponse(String phone);

    void onCallSenderResponse1(String phone);

    void onUpdateNumberReceiverResponse(String phone, DismissDialogCallback callback);

    void onUpdateNumberSenderResponse(String phone, DismissDialogCallback callback);

    void onCallCSKH(String phone);
}
