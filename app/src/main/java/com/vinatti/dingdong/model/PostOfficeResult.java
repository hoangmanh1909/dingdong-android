package com.vinatti.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class PostOfficeResult extends SimpleResult {
    @SerializedName("Value")
    private PostOffice postOffice;

    public PostOffice getPostOffice() {
        return postOffice;
    }
}
