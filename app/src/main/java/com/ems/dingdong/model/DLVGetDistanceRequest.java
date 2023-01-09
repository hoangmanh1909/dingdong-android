package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class DLVGetDistanceRequest {
    @SerializedName("From")
    public PointTinhKhoanCach From;
    @SerializedName("To")
    public PointTinhKhoanCach To;

    public PointTinhKhoanCach getFrom() {
        return From;
    }

    public void setFrom(PointTinhKhoanCach from) {
        From = from;
    }

    public PointTinhKhoanCach getTo() {
        return To;
    }

    public void setTo(PointTinhKhoanCach to) {
        To = to;
    }
}
