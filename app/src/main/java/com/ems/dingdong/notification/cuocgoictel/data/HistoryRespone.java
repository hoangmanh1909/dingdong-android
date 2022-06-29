package com.ems.dingdong.notification.cuocgoictel.data;

import com.google.gson.annotations.SerializedName;

public class HistoryRespone {
    @SerializedName("ToNumber")
    private String ToNumber;
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("StartTime")
    private String StartTime;
    @SerializedName("Status")
    private String Status;
    @SerializedName("CallTypeName")
    private String CallTypeName;
    @SerializedName("RecordFile")
    private String RecordFile;

    public String getToNumber() {
        return ToNumber;
    }

    public void setToNumber(String toNumber) {
        ToNumber = toNumber;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCallTypeName() {
        return CallTypeName;
    }

    public void setCallTypeName(String callTypeName) {
        CallTypeName = callTypeName;
    }

    public String getRecordFile() {
        return RecordFile;
    }

    public void setRecordFile(String recordFile) {
        RecordFile = recordFile;
    }
}
