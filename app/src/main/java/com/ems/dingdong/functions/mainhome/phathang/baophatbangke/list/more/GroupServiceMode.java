package com.ems.dingdong.functions.mainhome.phathang.baophatbangke.list.more;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupServiceMode {
    @SerializedName("Code")
    @Expose
    private String Code;
    @SerializedName("Name")
    @Expose
    private String Name;
    boolean isCheck = false;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
