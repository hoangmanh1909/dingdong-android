package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ShiftInfo {
    @SerializedName("ShiftId")
    String shiftId;
    @SerializedName("ShiftName")
    String shiftName;

    public String getShiftId() {
        return shiftId;
    }

    public String getShiftName() {
        return shiftName;
    }
}
