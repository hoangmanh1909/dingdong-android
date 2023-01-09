package com.ems.dingdong.model;

import com.ems.dingdong.functions.mainhome.address.danhbadichi.model.Info;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VmapPlace {
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("ward")
    @Expose
    private String ward;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("subVillage")
    @Expose
    private String subVillage;
    @SerializedName("stName")
    @Expose
    private String stName;
    @SerializedName("hsNum")
    @Expose
    private String hsNum;
    @SerializedName("alleyLv1")
    @Expose
    private String alleyLv1;
    @SerializedName("alleyLv2")
    @Expose
    private String alleyLv2;
    @SerializedName("plusCode")
    @Expose
    private String plusCode;
    @SerializedName("ndasCode")
    @Expose
    private String ndasCode;
    @SerializedName("nameBuilding")
    @Expose
    private String nameBuilding;
    @SerializedName("nameHousingArea")
    @Expose
    private String nameHousingArea;
    @SerializedName("lobbyBulding")
    @Expose
    private String lobbyBulding;
    @SerializedName("numberFloorBulding")
    @Expose
    private String numberFloorBulding;
    @SerializedName("numberRoomBulding")
    @Expose
    private String numberRoomBulding;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Postcode")
    @Expose
    private String Postcode;
    @SerializedName("verify")
    @Expose
    private Boolean verify;
    @SerializedName("info")
    @Expose
    private Info info;

    public String getPostcode() {
        return Postcode;
    }

    public void setPostcode(String postcode) {
        Postcode = postcode;
    }

    String placeid;

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getSubVillage() {
        return subVillage;
    }

    public void setSubVillage(String subVillage) {
        this.subVillage = subVillage;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getHsNum() {
        return hsNum;
    }

    public void setHsNum(String hsNum) {
        this.hsNum = hsNum;
    }

    public String getAlleyLv1() {
        return alleyLv1;
    }

    public void setAlleyLv1(String alleyLv1) {
        this.alleyLv1 = alleyLv1;
    }

    public String getAlleyLv2() {
        return alleyLv2;
    }

    public void setAlleyLv2(String alleyLv2) {
        this.alleyLv2 = alleyLv2;
    }

    public String getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(String plusCode) {
        this.plusCode = plusCode;
    }

    public String getNdasCode() {
        return ndasCode;
    }

    public void setNdasCode(String ndasCode) {
        this.ndasCode = ndasCode;
    }

    public String getNameBuilding() {
        return nameBuilding;
    }

    public void setNameBuilding(String nameBuilding) {
        this.nameBuilding = nameBuilding;
    }

    public String getNameHousingArea() {
        return nameHousingArea;
    }

    public void setNameHousingArea(String nameHousingArea) {
        this.nameHousingArea = nameHousingArea;
    }

    public String getLobbyBulding() {
        return lobbyBulding;
    }

    public void setLobbyBulding(String lobbyBulding) {
        this.lobbyBulding = lobbyBulding;
    }

    public String getNumberFloorBulding() {
        return numberFloorBulding;
    }

    public void setNumberFloorBulding(String numberFloorBulding) {
        this.numberFloorBulding = numberFloorBulding;
    }

    public String getNumberRoomBulding() {
        return numberRoomBulding;
    }

    public void setNumberRoomBulding(String numberRoomBulding) {
        this.numberRoomBulding = numberRoomBulding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
