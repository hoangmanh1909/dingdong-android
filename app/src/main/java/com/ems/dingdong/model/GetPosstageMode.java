package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPosstageMode {
    @SerializedName("PartnerSourceId")
    @Expose
    private String PartnerSourceId ;

    public String getPartnerSourceId() {
        return PartnerSourceId;
    }

    public void setPartnerSourceId(String partnerSourceId) {
        PartnerSourceId = partnerSourceId;
    }
}
