package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallTomeRequest {
    /// <summary>
    /// SĐT nhận cuộc gọi
    /// </summary>
    @SerializedName("ToNumber")
    @Expose
    public String ToNumber;
    @SerializedName("LadingCode")
    @Expose
    public String LadingCode;
    @SerializedName("PostmanCode")
    @Expose
    public String PostmanCode;
    @SerializedName("POCode")
    @Expose
    public String POCode;

    /// <summary>
    /// Loại cuộc gọi
    /// 1 - Gọi cho người gửi
    /// 2 - Gọi cho người nhận
    /// </summary>
    @SerializedName("CallType")
    @Expose
    public int CallType;
    @SerializedName("EmpGroupID")
    @Expose
    public int EmpGroupID;
    @SerializedName("PostmanId")
    @Expose
    public long PostmanId;
 @SerializedName("PostmanTel")
    @Expose
    public String PostmanTel ;

    public long getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(long postmanId) {
        PostmanId = postmanId;
    }

    public String getPostmanTel() {
        return PostmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        PostmanTel = postmanTel;
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

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public int getCallType() {
        return CallType;
    }

    public void setCallType(int callType) {
        CallType = callType;
    }

    public int getEmpGroupID() {
        return EmpGroupID;
    }

    public void setEmpGroupID(int empGroupID) {
        EmpGroupID = empGroupID;
    }
}
