package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallLiveMode {
    @SerializedName("FromNumber")
    @Expose
    public String FromNumber;
    /// <summary>
    /// Mã bc của bưu tá
    /// </summary>
    @SerializedName("ToNumber")
    @Expose
    public String ToNumber;
    /// <summary>
    /// Mã bưu tá
    /// </summary>
    @SerializedName("LadingCode")
    @Expose
    public String LadingCode;

    public String getFromNumber() {
        return FromNumber;
    }

    public void setFromNumber(String fromNumber) {
        FromNumber = fromNumber;
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
}
