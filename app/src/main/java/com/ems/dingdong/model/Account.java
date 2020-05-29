package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("company_id")
    String companyId;

    @SerializedName("vpbx")
    String vpbx;

    @SerializedName("extension")
    String extension;

    public String getCompanyId() {
        return companyId;
    }

    public String getVpbx() {
        return vpbx;
    }

    public String getExtension() {
        return extension;
    }
}
