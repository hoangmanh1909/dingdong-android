package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LinkHistory {
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
    @SerializedName("PostmanTel")
    @Expose
    public String PostmanTel;

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

    public String getPostmanTel() {
        return PostmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        PostmanTel = postmanTel;
    }
}
