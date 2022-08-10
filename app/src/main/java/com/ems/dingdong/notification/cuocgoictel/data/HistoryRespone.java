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
    @SerializedName("FromNumber")
    private String FromNumber;
    @SerializedName("EndTime")
    private String EndTime;
    @SerializedName("RecordFile")
    private String RecordFile;
    @SerializedName("AnswerDuration")
    private int AnswerDuration;
    @SerializedName("ApplicationName")
    private String ApplicationName;

    public String getApplicationName() {
        return ApplicationName;
    }

    public void setApplicationName(String applicationName) {
        ApplicationName = applicationName;
    }

    public String getFromNumber() {
        return FromNumber;
    }

    public void setFromNumber(String fromNumber) {
        FromNumber = fromNumber;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public int getAnswerDuration() {
        return AnswerDuration;
    }

    public void setAnswerDuration(int answerDuration) {
        AnswerDuration = answerDuration;
    }

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
