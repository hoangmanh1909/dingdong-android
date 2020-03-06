package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class PointMyVNPost {
    @SerializedName("FullAddress")
    String fullAdress;

    @SerializedName("Point")
    Point point;

    public String getFullAdress() {
        return fullAdress;
    }

    public Point getPoint() {
        return point;
    }
}
