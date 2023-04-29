package com.ems.dingdong.model.vmapmode;

import com.google.gson.annotations.SerializedName;

public class ValuesVmap {
    @SerializedName("speed")
    int  speed;
    @SerializedName("heading")
    String heading;
    @SerializedName("x")
    String x;
    @SerializedName("y")
    String y;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
