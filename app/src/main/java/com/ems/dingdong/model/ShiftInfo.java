package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class ShiftInfo {
    @SerializedName("ShiftId")
    String shiftId;
    @SerializedName("ShiftName")
    String shiftName;
    @SerializedName("FromTime")
    private String fromTime;
    @SerializedName("ToTime")
    private String toTime;
    public String getShiftId() {
        return shiftId;
    }

    public String getShiftName() {
        return shiftName;
    }

    public String getFromTime() {
        return fromTime;
    }

    public String getToTime() {
        return toTime;
    }
}
