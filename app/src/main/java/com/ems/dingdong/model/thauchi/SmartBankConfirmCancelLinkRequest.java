package com.ems.dingdong.model.thauchi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmartBankConfirmCancelLinkRequest {
    /// <summary>
    /// Mã ngân hàng
    /// </summary>
    @SerializedName("BankCode")
    @Expose
    public String BankCode;
    /// <summary>
    /// Mã bc của bưu tá
    /// </summary>
    @SerializedName("POCode")
    @Expose
    public String POCode;
    /// <summary>
    /// Mã bưu tá
    /// </summary>
    @SerializedName("PostmanCode")
    @Expose
    public String PostmanCode;

    @SerializedName("OTP")
    @Expose
    public String OTP;

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getPostmanCode() {
        return PostmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        PostmanCode = postmanCode;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
