package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class CreateVietMapRequest extends BaseRequestModel {
    @SerializedName("Housenumber")
    private String Housenumber;
    @SerializedName("Name")
    private String Name;
    @SerializedName("CategoryID")
    private Integer CategoryID;
    @SerializedName("StreetName")
    private String StreetName;
    @SerializedName("CityID")
    private Integer CityID;
    @SerializedName("DistID")
    private Integer DistID;
    @SerializedName("WardID")
    private Integer WardID;
    @SerializedName("Village")
    private String Village;
    @SerializedName("Longitude")
    private double Longitude;
    @SerializedName("Latitude")
    private double Latitude;
    @SerializedName("Id")
    private long  Id;
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


    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public Integer getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(Integer categoryID) {
        CategoryID = categoryID;
    }

    public Integer getCityID() {
        return CityID;
    }

    public void setCityID(Integer cityID) {
        CityID = cityID;
    }

    public Integer getDistID() {
        return DistID;
    }

    public void setDistID(Integer distID) {
        DistID = distID;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Integer getWardID() {
        return WardID;
    }

    public void setWardID(Integer wardID) {
        WardID = wardID;
    }


    public void setId(Integer id) {
        Id = id;
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
