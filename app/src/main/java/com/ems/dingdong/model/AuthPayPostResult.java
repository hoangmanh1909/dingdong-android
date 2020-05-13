package com.ems.dingdong.model;

import com.ems.dingdong.model.response.AuthPayPostResponse;
import com.google.gson.annotations.SerializedName;

public class AuthPayPostResult extends SimplePaypostResult {
    @SerializedName("data")
    AuthPayPostResponse authPayPostResponseList;

    public AuthPayPostResponse getAuthPayPostResponseList() {
        return authPayPostResponseList;
    }
}
