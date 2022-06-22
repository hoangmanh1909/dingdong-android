package com.ems.dingdong.model.thauchi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DanhSachNganHangRepsone {
    @SerializedName("BankCode")
    @Expose
    public String BankCode;
    @SerializedName("BankName")
    @Expose
    public String BankName;
    @SerializedName("GroupLogo")
    @Expose
    public String Logo;
    @SerializedName("GroupType")
    @Expose
    public int GroupType;
    @SerializedName("GroupName")
    @Expose
    public String GroupName;

    public int getGroupType() {
        return GroupType;
    }

    public void setGroupType(int groupType) {
        GroupType = groupType;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getBankCode() {
        return BankCode;
    }

    public void setBankCode(String bankCode) {
        BankCode = bankCode;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }
}
