package com.ems.dingdong.notification.cuocgoictel.data;

import com.google.gson.annotations.SerializedName;

public class NotiCtelModel {
    @SerializedName("TicketCode")
    private String TicketCode;
    @SerializedName("CreatedDate")
    private String CreatedDate;
    @SerializedName("LadingCode")
    private String LadingCode;
    @SerializedName("ReceiverName")
    private String ReceiverName;
    @SerializedName("ReceiverTel")
    private String ReceiverTel;
    @SerializedName("SolutionName")
    private String SolutionName;
    @SerializedName("StatusName")
    private String StatusName;
    @SerializedName("RecordUrl")
    private String RecordUrl;
    @SerializedName("CalledAt")
    private String CalledAt;

    public String getTicketCode() {
        return TicketCode;
    }

    public void setTicketCode(String ticketCode) {
        TicketCode = ticketCode;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getLadingCode() {
        return LadingCode;
    }

    public void setLadingCode(String ladingCode) {
        LadingCode = ladingCode;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getReceiverTel() {
        return ReceiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        ReceiverTel = receiverTel;
    }

    public String getSolutionName() {
        return SolutionName;
    }

    public void setSolutionName(String solutionName) {
        SolutionName = solutionName;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public String getRecordUrl() {
        return RecordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        RecordUrl = recordUrl;
    }

    public String getCalledAt() {
        return CalledAt;
    }

    public void setCalledAt(String calledAt) {
        CalledAt = calledAt;
    }
}
