package com.ems.dingdong.functions.mainhome.gomhang.listcommon.more;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DichVuMode {
    @SerializedName("Code")
    @Expose
    private String Code;
    @SerializedName("Name")
    @Expose
    private String Name;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
