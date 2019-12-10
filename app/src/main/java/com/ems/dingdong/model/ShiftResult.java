package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShiftResult extends SimpleResult {
    @SerializedName("ListValue")
    private ArrayList<ShiftInfo> shiftInfos;

    public ArrayList<ShiftInfo> getShiftInfos() {
        return shiftInfos;
    }
}
