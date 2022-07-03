package com.ems.dingdong.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmartBankLink {
    @SerializedName("BankCode")
    @Expose
    private String bankCode;
    @SerializedName("PostmanCode")
    @Expose
    private String postmanCode;
    @SerializedName("PostmanTel")
    @Expose
    private String postmanTel;
    @SerializedName("BankAccountName")
    @Expose
    private String bankAccountName;
    @SerializedName("PIDType")
    @Expose
    private String pIDType;
    @SerializedName("PIDNumber")
    @Expose
    private String pIDNumber;
    @SerializedName("BankAccountNumber")
    @Expose
    private String bankAccountNumber;
    @SerializedName("BankAccountLimit")
    @Expose
    private String bankAccountLimit;
    @SerializedName("BankAccountLimitExpired")
    @Expose
    private String bankAccountLimitExpired;
    @SerializedName("POCode")
    @Expose
    private String pOCode;
    @SerializedName("POName")
    @Expose
    private String pOName;
    @SerializedName("PaymentToken")
    @Expose
    private String paymentToken;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("GroupName")
    @Expose
    private String GroupName;
    @SerializedName("BankName")
    @Expose
    private String BankName;
    @SerializedName("BankLogo")
    @Expose
    private String BankLogo;
    @SerializedName("GroupLogo")
    @Expose
    private String GroupLogo;
    @SerializedName("IsDefaultPayment")
    @Expose
    private Boolean isDefaultPayment;
    @SerializedName("IsShowOTPPayment")
    @Expose
    private Boolean IsShowOTPPayment;

    @SerializedName("OTPLength")
    @Expose
    private int OTPLength;
    @SerializedName("OTPType")
    @Expose
    private int OTPType;
    @SerializedName("GroupType")
    @Expose
    private int GroupType;


    public SmartBankLink() {
    }

    public int getGroupType() {
        return GroupType;
    }

    public String getGroupLogo() {
        return GroupLogo;
    }

    public void setGroupLogo(String groupLogo) {
        GroupLogo = groupLogo;
    }

    public void setGroupType(int groupType) {
        GroupType = groupType;
    }

    public String getpIDType() {
        return pIDType;
    }

    public void setpIDType(String pIDType) {
        this.pIDType = pIDType;
    }

    public String getpIDNumber() {
        return pIDNumber;
    }

    public void setpIDNumber(String pIDNumber) {
        this.pIDNumber = pIDNumber;
    }

    public String getpOCode() {
        return pOCode;
    }

    public void setpOCode(String pOCode) {
        this.pOCode = pOCode;
    }

    public String getpOName() {
        return pOName;
    }

    public void setpOName(String pOName) {
        this.pOName = pOName;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public Boolean getDefaultPayment() {
        return isDefaultPayment;
    }

    public void setDefaultPayment(Boolean defaultPayment) {
        isDefaultPayment = defaultPayment;
    }

    public Boolean getShowOTPPayment() {
        return IsShowOTPPayment;
    }

    public void setShowOTPPayment(Boolean showOTPPayment) {
        IsShowOTPPayment = showOTPPayment;
    }

    public int getOTPLength() {
        return OTPLength;
    }

    public void setOTPLength(int OTPLength) {
        this.OTPLength = OTPLength;
    }

    public int getOTPType() {
        return OTPType;
    }

    public void setOTPType(int OTPType) {
        this.OTPType = OTPType;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankLogo() {
        return BankLogo;
    }

    public void setBankLogo(String bankLogo) {
        BankLogo = bankLogo;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getPostmanCode() {
        return postmanCode;
    }

    public void setPostmanCode(String postmanCode) {
        this.postmanCode = postmanCode;
    }

    public String getPostmanTel() {
        return postmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        this.postmanTel = postmanTel;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getPIDType() {
        return pIDType;
    }

    public void setPIDType(String pIDType) {
        this.pIDType = pIDType;
    }

    public String getPIDNumber() {
        return pIDNumber;
    }

    public void setPIDNumber(String pIDNumber) {
        this.pIDNumber = pIDNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountLimit() {
        return bankAccountLimit;
    }

    public void setBankAccountLimit(String bankAccountLimit) {
        this.bankAccountLimit = bankAccountLimit;
    }

    public String getBankAccountLimitExpired() {
        return bankAccountLimitExpired;
    }

    public void setBankAccountLimitExpired(String bankAccountLimitExpired) {
        this.bankAccountLimitExpired = bankAccountLimitExpired;
    }

    public String getPOCode() {
        return pOCode;
    }

    public void setPOCode(String pOCode) {
        this.pOCode = pOCode;
    }

    public String getPOName() {
        return pOName;
    }

    public void setPOName(String pOName) {
        this.pOName = pOName;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsDefaultPayment() {
        return isDefaultPayment;
    }

    public void setIsDefaultPayment(Boolean isDefaultPayment) {
        this.isDefaultPayment = isDefaultPayment;
    }
}
