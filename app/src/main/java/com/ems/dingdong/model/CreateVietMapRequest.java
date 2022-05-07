package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class CreateVietMapRequest extends BaseRequestModel {
    @SerializedName("Housenumber")
    private String Housenumber;
    @SerializedName("Name")
    private String Name;
    @SerializedName("CategoryID")
    private String CategoryID;
    @SerializedName("StreetName")
    private String StreetName;
    @SerializedName("CityID")
    private String CityID;
    @SerializedName("DistID")
    private String DistID;
    @SerializedName("WardID")
    private String WardID;
    @SerializedName("Village")
    private String Village;
    @SerializedName("Longitude")
    private double Longitude;
    @SerializedName("Latitude")
    private double Latitude;
    @SerializedName("Id")
    private String Id;
    @SerializedName("CityName")
    private String CityName = "";
    @SerializedName("DistrictName")
    private String DistrictName = "";
    @SerializedName("WardName")
    private String WardName = "";
    @SerializedName("Phone")
    private String Phone;
    @SerializedName("Type")
    private int Type;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getHousenumber() {
        return Housenumber;
    }

    public void setHousenumber(String housenumber) {
        Housenumber = housenumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getDistID() {
        return DistID;
    }

    public void setDistID(String distID) {
        DistID = distID;
    }

    public String getWardID() {
        return WardID;
    }

    public void setWardID(String wardID) {
        WardID = wardID;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String village) {
        Village = village;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getWardName() {
        return WardName;
    }

    public void setWardName(String wardName) {
        WardName = wardName;
    }
}
