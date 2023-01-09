package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyInfo {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameCty")
    @Expose
    private String nameCty;
    @SerializedName("representativeName")
    @Expose
    private String representativeName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("backlist")
    @Expose
    private Boolean backlist;
    @SerializedName("isVerifyEmail")
    @Expose
    private Boolean isVerifyEmail;
    @SerializedName("isVerifyPhone")
    @Expose
    private Boolean isVerifyPhone;
    @SerializedName("lobbyBulding")
    @Expose
    private String lobbyBulding;
    @SerializedName("numberFloorBulding")
    @Expose
    private String numberFloorBulding;
    @SerializedName("numberRoomBulding")
    @Expose
    private String numberRoomBulding;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCty() {
        return nameCty;
    }

    public void setNameCty(String nameCty) {
        this.nameCty = nameCty;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getBacklist() {
        return backlist;
    }

    public void setBacklist(Boolean backlist) {
        this.backlist = backlist;
    }

    public Boolean getVerifyEmail() {
        return isVerifyEmail;
    }

    public void setVerifyEmail(Boolean verifyEmail) {
        isVerifyEmail = verifyEmail;
    }

    public Boolean getVerifyPhone() {
        return isVerifyPhone;
    }

    public void setVerifyPhone(Boolean verifyPhone) {
        isVerifyPhone = verifyPhone;
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
}
