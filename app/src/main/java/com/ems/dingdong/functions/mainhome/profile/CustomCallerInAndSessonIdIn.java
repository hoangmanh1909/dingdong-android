package com.ems.dingdong.functions.mainhome.profile;

public class CustomCallerInAndSessonIdIn {
    String numberCustomer;
    String sessionId;

    public CustomCallerInAndSessonIdIn(String numberCustomer, String sessionId) {
        this.numberCustomer = numberCustomer;
        this.sessionId = sessionId;
    }

    public String getNumberCustomer() {
        return numberCustomer;
    }

    public void setNumberCustomer(String numberCustomer) {
        this.numberCustomer = numberCustomer;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
