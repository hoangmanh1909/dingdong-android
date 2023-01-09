package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DICRouteAddressBookAddInfoUserCreateRequest {
    @SerializedName("Type")
    int Type;
    @SerializedName("Name")
    String Name;
    @SerializedName("Phone")
    String Phone;
    @SerializedName("Email")
    String Email;
    @SerializedName("Note")
    String Note;
    @SerializedName("LobbyBuilding")
    String LobbyBuilding;
    @SerializedName("NumberFloorBuilding")
    String NumberFloorBuilding;
    @SerializedName("NumberRoomBuilding")
    String NumberRoomBuilding;
    @SerializedName("ServiceNotes")
    String ServiceNotes;
    @SerializedName("NamePersons")
    String NamePersons;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getLobbyBuilding() {
        return LobbyBuilding;
    }

    public void setLobbyBuilding(String lobbyBuilding) {
        LobbyBuilding = lobbyBuilding;
    }

    public String getNumberFloorBuilding() {
        return NumberFloorBuilding;
    }

    public void setNumberFloorBuilding(String numberFloorBuilding) {
        NumberFloorBuilding = numberFloorBuilding;
    }

    public String getNumberRoomBuilding() {
        return NumberRoomBuilding;
    }

    public void setNumberRoomBuilding(String numberRoomBuilding) {
        NumberRoomBuilding = numberRoomBuilding;
    }

    public String getServiceNotes() {
        return ServiceNotes;
    }

    public void setServiceNotes(String serviceNotes) {
        ServiceNotes = serviceNotes;
    }

    public String getNamePersons() {
        return NamePersons;
    }

    public void setNamePersons(String namePersons) {
        NamePersons = namePersons;
    }
}
