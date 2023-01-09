package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DICRouteAddressBookAddInfoCreateRequest {
    @SerializedName("AddInfoUsers")
    List<DICRouteAddressBookAddInfoUserCreateRequest> AddInfoUsers;
    @SerializedName("Note")
    String Note;
    @SerializedName("Instruction")
    String Instruction;

    public List<DICRouteAddressBookAddInfoUserCreateRequest> getAddInfoUsers() {
        return AddInfoUsers;
    }

    public void setAddInfoUsers(List<DICRouteAddressBookAddInfoUserCreateRequest> addInfoUsers) {
        AddInfoUsers = addInfoUsers;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }
}
