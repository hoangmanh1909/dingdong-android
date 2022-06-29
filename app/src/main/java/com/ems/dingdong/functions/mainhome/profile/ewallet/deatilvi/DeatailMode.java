package com.ems.dingdong.functions.mainhome.profile.ewallet.deatilvi;

import com.google.gson.annotations.SerializedName;

public class DeatailMode {
    @SerializedName("AmndDate")
    private String AmndDate;

    @SerializedName("ActionName")
    private String ActionName;

    public String getAmndDate() {
        return AmndDate;
    }

    public void setAmndDate(String amndDate) {
        AmndDate = amndDate;
    }

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }
}
