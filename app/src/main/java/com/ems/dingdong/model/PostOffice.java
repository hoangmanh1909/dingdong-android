package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostOffice {

    @SerializedName("Code")
    String Code;
    @SerializedName("Name")
    String Name;
    @SerializedName("CommuneCode")
    String CommuneCode;
    @SerializedName("ParentCode")
    String ParentCode;
    @SerializedName("HolineNumber")
    String HolineNumber;
//    @SerializedName("RouteCode")
//    String RouteCode;
//    @SerializedName("RouteName")
//    String RouteName;
    @SerializedName("Routes")
    List<RouteInfo> Routes;

    public List<RouteInfo> getRoutes() {
        return Routes;
    }

    public void setRoutes(List<RouteInfo> routes) {
        Routes = routes;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCommuneCode() {
        return CommuneCode;
    }

    public void setCommuneCode(String communeCode) {
        CommuneCode = communeCode;
    }

    public String getParentCode() {
        return ParentCode;
    }

    public void setParentCode(String parentCode) {
        ParentCode = parentCode;
    }

    public String getHolineNumber() {
        return HolineNumber;
    }

    public void setHolineNumber(String holineNumber) {
        HolineNumber = holineNumber;
    }
//
//    public String getRouteCode() {
//        return RouteCode;
//    }
//
//    public String getRouteName() {
//        return RouteName;
//    }
}
