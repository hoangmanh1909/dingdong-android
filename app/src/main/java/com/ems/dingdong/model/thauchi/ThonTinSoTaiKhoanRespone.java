package com.ems.dingdong.model.thauchi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThonTinSoTaiKhoanRespone {
    @SerializedName("PostofficeId")
    @Expose
    public String PostofficeId;
    @SerializedName("PostmanId")
    @Expose
    public String postmanId;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("PostmanTel")
    @Expose
    public String postmanTel;
    @SerializedName("LegalDocName")
    @Expose
    public String legalDocName;
    @SerializedName("LegalId")
    @Expose
    public String legalId;
    @SerializedName("LegalIssueDate")
    @Expose
    public String legalIssueDate;
    @SerializedName("LegalIssueAuth")
    @Expose
    public String legalIssueAuth;
    @SerializedName("AccountNumber")
    @Expose
    public String account;
    @SerializedName("AccountLimit")
    @Expose
    public String accountLimit;
    @SerializedName("OverdraftDate")
    @Expose
    public String overdraftDate;
    @SerializedName("PostmanStatus")
    @Expose
    public String postmanStatus;
    @SerializedName("Status")
    @Expose
    public String status;

    public String getPostofficeId() {
        return PostofficeId;
    }

    public void setPostofficeId(String postofficeId) {
        PostofficeId = postofficeId;
    }

    public String getPostmanId() {
        return postmanId;
    }

    public void setPostmanId(String postmanId) {
        this.postmanId = postmanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostmanTel() {
        return postmanTel;
    }

    public void setPostmanTel(String postmanTel) {
        this.postmanTel = postmanTel;
    }

    public String getLegalDocName() {
        return legalDocName;
    }

    public void setLegalDocName(String legalDocName) {
        this.legalDocName = legalDocName;
    }

    public String getLegalId() {
        return legalId;
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId;
    }

    public String getLegalIssueDate() {
        return legalIssueDate;
    }

    public void setLegalIssueDate(String legalIssueDate) {
        this.legalIssueDate = legalIssueDate;
    }

    public String getLegalIssueAuth() {
        return legalIssueAuth;
    }

    public void setLegalIssueAuth(String legalIssueAuth) {
        this.legalIssueAuth = legalIssueAuth;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(String accountLimit) {
        this.accountLimit = accountLimit;
    }

    public String getOverdraftDate() {
        return overdraftDate;
    }

    public void setOverdraftDate(String overdraftDate) {
        this.overdraftDate = overdraftDate;
    }

    public String getPostmanStatus() {
        return postmanStatus;
    }

    public void setPostmanStatus(String postmanStatus) {
        this.postmanStatus = postmanStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
