package com.ems.dingdong.model;

public class CollectReason {
    String ReasonCode;
    String ReasonName;

    public CollectReason(String reasonCode, String reasonName) {
        ReasonCode = reasonCode;
        ReasonName = reasonName;
    }

    public String getReasonCode() {
        return ReasonCode;
    }

    public String getReasonName() {
        return ReasonName;
    }
}
