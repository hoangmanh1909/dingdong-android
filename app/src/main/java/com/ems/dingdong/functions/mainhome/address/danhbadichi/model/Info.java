package com.ems.dingdong.functions.mainhome.address.danhbadichi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Info {
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("personInHouses")
    @Expose
    private List<PersonInHouse> personInHouses = null;
    @SerializedName("companyInfo")
    @Expose
    private List<CompanyInfo> companyInfo = null;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<PersonInHouse> getPersonInHouses() {
        return personInHouses;
    }

    public void setPersonInHouses(List<PersonInHouse> personInHouses) {
        this.personInHouses = personInHouses;
    }

    public List<CompanyInfo> getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(List<CompanyInfo> companyInfo) {
        this.companyInfo = companyInfo;
    }
}
