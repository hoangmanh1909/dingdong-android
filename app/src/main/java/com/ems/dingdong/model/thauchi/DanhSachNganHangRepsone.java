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
    @SerializedName("Logo")
    @Expose
    public String Logo;
    @SerializedName("GroupLogo")
    @Expose
    public String groupLogo;
    @SerializedName("GroupType")
    @Expose
    public Integer groupType;

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


    public String getGroupLogo() {
        return groupLogo;
    }

    public void setGroupLogo(String groupLogo) {
        this.groupLogo = groupLogo;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }
}
