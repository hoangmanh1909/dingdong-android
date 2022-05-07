package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataCateModel {

    @SerializedName("id")
    private int id;
    @SerializedName("level")
    private int level;
    @SerializedName("name")
    private String name;
    @SerializedName("subCategories")
    private List<SubCategoryModel> subCategories;

    public List<SubCategoryModel> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategoryModel> subCategories) {
        this.subCategories = subCategories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
