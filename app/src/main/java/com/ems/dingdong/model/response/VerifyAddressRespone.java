package com.ems.dingdong.model.response;

import android.annotation.SuppressLint;

import com.ems.dingdong.model.Values;
import com.ems.dingdong.model.SimpleResult;
import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class VerifyAddressRespone extends SimpleResult {
    @SerializedName("Value")
    public Values Value;

    public Values getValue() {
        return Value;
    }

    public void setValue(Values value) {
        Value = value;
    }
}
