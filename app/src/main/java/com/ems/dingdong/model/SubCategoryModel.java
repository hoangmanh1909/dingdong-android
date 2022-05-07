package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryModel {
    @SerializedName("id")
    public int id;
    @SerializedName("level")
    public String level;
    @SerializedName("name")
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
