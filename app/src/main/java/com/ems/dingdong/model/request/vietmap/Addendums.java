package com.ems.dingdong.model.request.vietmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Addendums {
    @SerializedName("vnpost")
    @Expose
    private String vnpost;

    public String getVnpost() {
        return vnpost;
    }

    public void setVnpost(String vnpost) {
        this.vnpost = vnpost;
    }
}
