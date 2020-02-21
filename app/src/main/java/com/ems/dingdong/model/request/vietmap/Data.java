package com.ems.dingdong.model.request.vietmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("aliasNames")
    @Expose
    private List<String> aliasNames = null;
    @SerializedName("housenumber")
    @Expose
    private String housenumber;
    @SerializedName("streetName")
    @Expose
    private String streetName;
    @SerializedName("building")
    @Expose
    private String building;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("stPrefix")
    @Expose
    private String stPrefix;
    @SerializedName("centerPoint")
    @Expose
    private CenterPoint centerPoint;
    @SerializedName("addendums")
    @Expose
    private Addendums addendums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAliasNames() {
        return aliasNames;
    }

    public void setAliasNames(List<String> aliasNames) {
        this.aliasNames = aliasNames;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStPrefix() {
        return stPrefix;
    }

    public void setStPrefix(String stPrefix) {
        this.stPrefix = stPrefix;
    }

    public CenterPoint getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(CenterPoint centerPoint) {
        this.centerPoint = centerPoint;
    }

    public Addendums getAddendums() {
        return addendums;
    }

    public void setAddendums(Addendums addendums) {
        this.addendums = addendums;
    }

}
