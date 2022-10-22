package com.ems.dingdong.functions.mainhome.gomhang.listcommon.more;

import com.ems.dingdong.model.CommonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Mpit {
    @SerializedName("ServiceCodeMPITS")
    @Expose
    private String ServiceCodeMPITS;
    @SerializedName("ServiceNameMPITS")
    @Expose
    private String ServiceNameMPITS;
    @SerializedName("CommonObject")
    @Expose
    private List<CommonObject> commonObject;

    boolean ischeck;

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public String getServiceCodeMPITS() {
        return ServiceCodeMPITS;
    }

    public void setServiceCodeMPITS(String serviceCodeMPITS) {
        ServiceCodeMPITS = serviceCodeMPITS;
    }

    public String getServiceNameMPITS() {
        return ServiceNameMPITS;
    }

    public void setServiceNameMPITS(String serviceNameMPITS) {
        ServiceNameMPITS = serviceNameMPITS;
    }

    public List<CommonObject> getCommonObject() {
        return commonObject;
    }

    public void setCommonObject(List<CommonObject> commonObject) {
        this.commonObject = commonObject;
    }
}
