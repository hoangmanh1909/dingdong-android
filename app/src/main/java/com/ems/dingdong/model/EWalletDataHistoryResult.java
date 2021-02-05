package com.ems.dingdong.model;

import android.annotation.SuppressLint;

import com.ems.dingdong.model.response.EWalletDataResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressLint("ParcelCreator")
public class EWalletDataHistoryResult extends SimpleResult {
    @SerializedName("Data")
    private String getList;

    public String getListCodes() {
        return getList;
    }

}
