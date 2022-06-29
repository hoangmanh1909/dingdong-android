package com.ems.dingdong.model.thauchi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmartBankRequestCancelLinkRequest {
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
    /// <summary>
    /// Loại GTTT
    /// 1 - CMND
    /// 2 - TCC
    /// 3 - PP
    /// </summary>
    @SerializedName("PIDType")
    @Expose
    public String PIDType;
    /// <summary>
    /// Số GTTT
    /// </summary>
    @SerializedName("PIDNumber")
    @Expose
    public String PIDNumber;
    /// <summary>
    /// Số tài khoản thấu chi SeABank
    /// </summary>
    @SerializedName("SeABankAccount")
    @Expose
    public String SeABankAccount;
    @SerializedName("SeABankAccountLimit")
    @Expose
    public String SeABankAccountLimit;
    @SerializedName("POProvinceCode")
    @Expose
    public String POProvinceCode;
    @SerializedName("PODistrictCode")
    @Expose
    public String PODistrictCode;
    @SerializedName("PostmanId")
    @Expose
    public String PostmanId;
    @SerializedName("RouteCode")
    @Expose
    public String RouteCode;
    @SerializedName("RouteId")
    @Expose
    public String RouteId;
    @SerializedName("Signature")
    @Expose
    public String Signature;
    @SerializedName("AccountType")
    @Expose
    public int AccountType;
    @SerializedName("PostmanTel")
    @Expose
    public String PostmanTel;
    @SerializedName("PaymentToken")
    @Expose
    public String PaymentToken;

    public String getPaymentToken() {
        return PaymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        PaymentToken = paymentToken;
    }

    public String getPostmanTel() {
        return PostmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        PostmanTel = postmanTel;
    }

    public String getPOProvinceCode() {
        return POProvinceCode;
    }

    public void setPOProvinceCode(String POProvinceCode) {
        this.POProvinceCode = POProvinceCode;
    }

    public String getPODistrictCode() {
        return PODistrictCode;
    }

    public void setPODistrictCode(String PODistrictCode) {
        this.PODistrictCode = PODistrictCode;
    }

    public String getPostmanId() {
        return PostmanId;
    }

    public void setPostmanId(String postmanId) {
        PostmanId = postmanId;
    }

    public String getRouteCode() {
        return RouteCode;
    }

    public void setRouteCode(String routeCode) {
        RouteCode = routeCode;
    }

    public String getRouteId() {
        return RouteId;
    }

    public void setRouteId(String routeId) {
        RouteId = routeId;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public int getAccountType() {
        return AccountType;
    }

    public void setAccountType(int accountType) {
        AccountType = accountType;
    }

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

    public String getPIDType() {
        return PIDType;
    }

    public void setPIDType(String PIDType) {
        this.PIDType = PIDType;
    }

    public String getPIDNumber() {
        return PIDNumber;
    }

    public void setPIDNumber(String PIDNumber) {
        this.PIDNumber = PIDNumber;
    }

    public String getSeABankAccount() {
        return SeABankAccount;
    }

    public void setSeABankAccount(String seABankAccount) {
        SeABankAccount = seABankAccount;
    }

    public String getSeABankAccountLimit() {
        return SeABankAccountLimit;
    }

    public void setSeABankAccountLimit(String seABankAccountLimit) {
        SeABankAccountLimit = seABankAccountLimit;
    }
}
