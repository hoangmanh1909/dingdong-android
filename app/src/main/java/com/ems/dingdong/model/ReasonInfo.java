package com.ems.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ReasonInfo extends RealmObject{
    @SerializedName("ID")
    @Expose
    @PrimaryKey
    private Integer id;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Solution")
    @Expose
    private String solution;

    public Integer getID() {
        return id;
    }

    public void setID(Integer iD) {
        this.id = iD;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public ReasonInfo() {
    }

    public ReasonInfo(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
