package com.vinatti.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryCallInfo  {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("AmndEmp")
    @Expose
    private String amndEmp;
    @SerializedName("PostmanID")
    @Expose
    private String postmanID;
    @SerializedName("PostmanName")
    @Expose
    private String postmanName;
    @SerializedName("SenderPhone")
    @Expose
    private String senderPhone;
    @SerializedName("ReceiverPhone")
    @Expose
    private String receiverPhone;
    @SerializedName("CallTime")
    @Expose
    private String callTime;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;

    public String getiD() {
        return iD;
    }

    public String getAmndEmp() {
        return amndEmp;
    }

    public String getPostmanID() {
        return postmanID;
    }

    public String getPostmanName() {
        return postmanName;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public String getCallTime() {
        return callTime;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
