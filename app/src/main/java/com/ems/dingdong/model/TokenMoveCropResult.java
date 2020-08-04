package com.ems.dingdong.model;

import com.google.gson.annotations.SerializedName;

public class TokenMoveCropResult extends SimpleResult{
    @SerializedName("Value")
    private ActiveResponse response;

    public ActiveResponse getResponse() {
        return response;
    }
}
