package com.ems.dingdong.model;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class ConfirmOrderPostmanResult extends SimpleResult {
    @SerializedName("Value")
    private ConfirmOrderPostman orderPostman;

    public ConfirmOrderPostman getOrderPostman() {
        return orderPostman;
    }
}
