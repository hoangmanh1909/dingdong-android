package com.vinatti.dingdong.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryCreateBd13Result extends SimpleResult {
    @SerializedName("ListValue")
    @Expose
    private List<Bd13Code> bd13Codes = null;

    public List<Bd13Code> getBd13Codes() {
        return bd13Codes;
    }
}
