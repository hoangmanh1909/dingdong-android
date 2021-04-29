package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaoTinReepone {
    @SerializedName("Id")
    public long Id;
    @SerializedName("CustomerCode")
    public String customerCode;
    @SerializedName("Contract")
    public String Contract;
    @SerializedName("ContactName")
    public String ContactName;
    @SerializedName("GeneralFullName")
    public String GeneralFullName;
    @SerializedName("GeneralShortName")
    public String GeneralShortName;
    @SerializedName("ContactAddress")
    public String ContactAddress;
    @SerializedName("ContactProvince")
    public String ContactProvince;
    @SerializedName("ContactDistrict")
    public String ContactDistrict;
    @SerializedName("ContactWard")
    public String ContactWard;
    @SerializedName("ContactStreet")
    public String ContactStreet;
    @SerializedName("ContactPhoneWork")
    public String ContactPhoneWork;
    @SerializedName("GeneralEmail")
    public String GeneralEmail;
    @SerializedName("ParentCustomerCode")
    public String ParentCustomerCode;

    @SerializedName("CustomerType")
    public String CustomerType;
    @SerializedName("ContactProvinceName")
    public String ContactProvinceName;
    @SerializedName("ContactDistrictName")
    public String ContactDistrictName;
    @SerializedName("ContactWardName")
    public String ContactWardName;
    @SerializedName("Contracts")
    public List<Contracts> ListContacts;

    public List<Contracts> getListContacts() {
        return ListContacts;
    }

    public void setListContacts(List<Contracts> listContacts) {
        ListContacts = listContacts;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getContract() {
        return Contract;
    }

    public void setContract(String contract) {
        Contract = contract;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getGeneralFullName() {
        return GeneralFullName;
    }

    public void setGeneralFullName(String generalFullName) {
        GeneralFullName = generalFullName;
    }

    public String getGeneralShortName() {
        return GeneralShortName;
    }

    public void setGeneralShortName(String generalShortName) {
        GeneralShortName = generalShortName;
    }

    public String getContactAddress() {
        return ContactAddress;
    }

    public void setContactAddress(String contactAddress) {
        ContactAddress = contactAddress;
    }

    public String getContactProvince() {
        return ContactProvince;
    }

    public void setContactProvince(String contactProvince) {
        ContactProvince = contactProvince;
    }

    public String getContactDistrict() {
        return ContactDistrict;
    }

    public void setContactDistrict(String contactDistrict) {
        ContactDistrict = contactDistrict;
    }

    public String getContactWard() {
        return ContactWard;
    }

    public void setContactWard(String contactWard) {
        ContactWard = contactWard;
    }

    public String getContactStreet() {
        return ContactStreet;
    }

    public void setContactStreet(String contactStreet) {
        ContactStreet = contactStreet;
    }

    public String getContactPhoneWork() {
        return ContactPhoneWork;
    }

    public void setContactPhoneWork(String contactPhoneWork) {
        ContactPhoneWork = contactPhoneWork;
    }

    public String getGeneralEmail() {
        return GeneralEmail;
    }

    public void setGeneralEmail(String generalEmail) {
        GeneralEmail = generalEmail;
    }

    public String getParentCustomerCode() {
        return ParentCustomerCode;
    }

    public void setParentCustomerCode(String parentCustomerCode) {
        ParentCustomerCode = parentCustomerCode;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getContactProvinceName() {
        return ContactProvinceName;
    }

    public void setContactProvinceName(String contactProvinceName) {
        ContactProvinceName = contactProvinceName;
    }

    public String getContactDistrictName() {
        return ContactDistrictName;
    }

    public void setContactDistrictName(String contactDistrictName) {
        ContactDistrictName = contactDistrictName;
    }

    public String getContactWardName() {
        return ContactWardName;
    }

    public void setContactWardName(String contactWardName) {
        ContactWardName = contactWardName;
    }
}
