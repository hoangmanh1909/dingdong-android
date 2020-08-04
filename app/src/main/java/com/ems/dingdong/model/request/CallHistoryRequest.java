package com.ems.dingdong.model.request;

public class CallHistoryRequest {

    private Integer tenantID;
    private Integer caller;
    private Integer callee;

    public Integer getTenantID() {
        return tenantID;
    }

    public void setTenantID(Integer tenantID) {
        this.tenantID = tenantID;
    }

    public Integer getCaller() {
        return caller;
    }

    public void setCaller(Integer caller) {
        this.caller = caller;
    }

    public Integer getCallee() {
        return callee;
    }

    public void setCallee(Integer callee) {
        this.callee = callee;
    }
}
