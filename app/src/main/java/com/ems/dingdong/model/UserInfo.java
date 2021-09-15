package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class UserInfo {

    @SerializedName("UnitCode")
    private String unitCode;
    @SerializedName("ID")
    private String iD;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("EmpGroupID")
    private String empGroupID;
    @SerializedName("UnitLink")
    private String unitLink;
    @SerializedName("MobileNumber")
    private String mobileNumber;
    @SerializedName("AmountMax")
    private String amountMax;
    @SerializedName("Balance")
    private String balance;
    @SerializedName("IsEms")
    private String isEms;
    @SerializedName("EWalletPaymentToken")
    private String eWalletPaymentToken;
    //
    @SerializedName("ExtensionUserName")
    private String extensionUserName;
    @SerializedName("ExtensionPassword")
    private String extensionPassword ;
    @SerializedName("ExtensionDomain")
    private String extensionDomain ;
    @SerializedName("ExtensionServer")
    private String extensionServer ;
    @SerializedName("ExtensionServerPort")
    private String extensionServerPort ;
    @SerializedName("ExtensionStunServer")
    private String extensionStunServer ;
    @SerializedName("ExtensionStunServerPort")
    private String extensionStunServerPort ;

    @SerializedName("POProvinceCode")
    private String POProvinceCode ;
    @SerializedName("PODistrictCode")
    private String PODistrictCode ;

    public UserInfo(String unitCode, String iD, String fullName, String userName, String empGroupID, String unitLink, String mobileNumber, String amountMax, String balance, String isEms, String eWalletPaymentToken, String extensionUserName, String extensionPassword, String extensionDomain, String extensionServer, String extensionServerPort, String extensionStunServer, String extensionStunServerPort) {
        this.unitCode = unitCode;
        this.iD = iD;
        this.fullName = fullName;
        this.userName = userName;
        this.empGroupID = empGroupID;
        this.unitLink = unitLink;
        this.mobileNumber = mobileNumber;
        this.amountMax = amountMax;
        this.balance = balance;
        this.isEms = isEms;
        this.eWalletPaymentToken = eWalletPaymentToken;
        this.extensionUserName = extensionUserName;
        this.extensionPassword = extensionPassword;
        this.extensionDomain = extensionDomain;
        this.extensionServer = extensionServer;
        this.extensionServerPort = extensionServerPort;
        this.extensionStunServer = extensionStunServer;
        this.extensionStunServerPort = extensionStunServerPort;
    }


    public String getUnitCode() {
        return unitCode;
    }

    public String getiD() {
        return iD;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmpGroupID() {
        return empGroupID;
    }

    public String getUnitLink() {
        return unitLink;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getAmountMax() {
        return amountMax;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIsEms() {
        return isEms;
    }

    public String geteWalletPaymentToken() {
        return eWalletPaymentToken;
    }


    public String getExtensionUserName() {
        return extensionUserName;
    }

    public String getExtensionPassword() {
        return extensionPassword;
    }

    public String getExtensionDomain() {
        return extensionDomain;
    }

    public String getExtensionServer() {
        return extensionServer;
    }

    public String getExtensionServerPort() {
        return extensionServerPort;
    }

    public String getExtensionStunServer() {
        return extensionStunServer;
    }

    public String getExtensionStunServerPort() {
        return extensionStunServerPort;
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
}
