package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class CallHistoryVHT {
    @SerializedName("lading_code")
    private String ladingCode;
    @SerializedName("po_province_code")
    private String poProvinceCode;
    @SerializedName("po_district_code")
    private String poDistrictCode;
    @SerializedName("po_code")
    private String poCode;
    @SerializedName("postman_code")
    private String postmanCode;
    @SerializedName("route_code")
    private String routeCode;
    @SerializedName("from_number")
    private String fromNumber;
    @SerializedName("to_number")
    private String toNumber;
    @SerializedName("call_id")
    private String callId;
    @SerializedName("session_id")
    private String sessionId;

    public CallHistoryVHT(String ladingCode, String poProvinceCode, String poDistrictCode, String poCode, String postmanCode, String routeCode, String fromNumber, String toNumber, String callId, String sessionId) {
        this.ladingCode = ladingCode;
        this.poProvinceCode = poProvinceCode;
        this.poDistrictCode = poDistrictCode;
        this.poCode = poCode;
        this.postmanCode = postmanCode;
        this.routeCode = routeCode;
        this.fromNumber = fromNumber;
        this.toNumber = toNumber;
        this.callId = callId;
        this.sessionId = sessionId;
    }

    public String getLadingCode() {
        return ladingCode;
    }

    public void setLadingCode(String ladingCode) {
        this.ladingCode = ladingCode;
    }

    public String getPoProvinceCode() {
        return poProvinceCode;
    }

    public void setPoProvinceCode(String poProvinceCode) {
        this.poProvinceCode = poProvinceCode;
    }

    public String getPoDistrictCode() {
        return poDistrictCode;
    }

    public void setPoDistrictCode(String poDistrictCode) {
        this.poDistrictCode = poDistrictCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
