package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuuCucHuyenMode {
    @SerializedName("POId")
    @Expose
    public long POId ;
    @SerializedName("POCode")
    @Expose
    public String POCode ;
    @SerializedName("POName")
    @Expose
    public String POName ;
    @SerializedName("UnitCode")
    @Expose
    public String UnitCode ;


    public long getPOId() {
        return POId;
    }

    public void setPOId(long POId) {
        this.POId = POId;
    }

    public String getPOCode() {
        return POCode;
    }

    public void setPOCode(String POCode) {
        this.POCode = POCode;
    }

    public String getPOName() {
        return POName;
    }

    public void setPOName(String POName) {
        this.POName = POName;
    }

    public String getUnitCode() {
        return UnitCode;
    }

    public void setUnitCode(String unitCode) {
        UnitCode = unitCode;
    }
}
