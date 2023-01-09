package com.ems.dingdong.functions.mainhome.profile.chat.menuchat.model;

import com.google.gson.annotations.SerializedName;

public class RequestQueuChat {
    @SerializedName("idDepartment")
    private String idDepartment;
    @SerializedName("idMission")
    private int idMission;

    public String getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(String idDepartment) {
        this.idDepartment = idDepartment;
    }

    public int getIdMission() {
        return idMission;
    }

    public void setIdMission(int idMission) {
        this.idMission = idMission;
    }
}
